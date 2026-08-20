package src.model;


import src.exception.ChatServerException;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatServer {

    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;

    private static final Path LOG_FILE = Path.of("data/chat_log.txt");
    private static final Path HISTORY_FILE = Path.of("data/chat_history.dat");

    private static final ObjectInputFilter ONLY_CHAT_MESSAGE =
            ObjectInputFilter.Config.createFilter("src.model.ChatMessage;java.lang.String;!*");

    private final List<ChatMessage> chatHistory = new ArrayList<>();
    private final List<SocketChannel> clients = new ArrayList<>();

    public void start() throws ChatServerException {

        try (
                ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()
        ) {

            serverSocketChannel.bind(new InetSocketAddress(PORT));

            serverSocketChannel.configureBlocking(false);

            Selector selector = Selector.open();

            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

            Runtime.getRuntime().addShutdownHook(new Thread(this::saveHistory));

            System.out.println("Server started on port " + PORT + ", messages in history: " + chatHistory.size());

            runEventLoop(selector, serverSocketChannel);

        } catch (IOException e) {
            throw new ChatServerException("Failed to start the server: " + e.getMessage());
        }

    }

    private void runEventLoop(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {

        while (true) {

            selector.select();

            Iterator<SelectionKey> iterator =
                    selector.selectedKeys().iterator();

            while (iterator.hasNext()) {

                SelectionKey key = iterator.next();

                iterator.remove();

                try {

                    if (!key.isValid()) {
                        continue;
                    }

                    if (key.isAcceptable()) {
                        acceptClient(selector, serverSocketChannel);
                    }

                    if (key.isReadable()) {
                        readFromClient(key);
                    }

                } catch (IOException | ChatServerException e) {
                    System.out.println("Problem with a client: " + e.getMessage());
                    disconnectClient(key);
                }

            }
        }

    }

    public void acceptClient(Selector selector, ServerSocketChannel serverSocketChannel) throws IOException {

        SocketChannel clientChannel = serverSocketChannel.accept();

        if (clientChannel == null) {
            return;
        }

        clientChannel.configureBlocking(false);

        clientChannel.register(selector, SelectionKey.OP_READ);

        clients.add(clientChannel);

        System.out.println("New client: " + clientChannel.getRemoteAddress() + ", total clients: " + clients.size());
    }

    public void readFromClient(SelectionKey key) throws IOException {

        SocketChannel clientChannel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        int readCount = clientChannel.read(buffer);

        if (readCount == -1) {
            disconnectClient(key);
            return;
        }

        if (readCount == 0) {
            return;
        }

        buffer.flip();

        byte[] data = new byte[buffer.remaining()];
        buffer.get(data);

        buffer.clear();

        ChatMessage message = deserializeMessage(data);

        System.out.println("Message received: " + message);

        chatHistory.add(message);
        writeToLog(message);
        broadcast(message, clientChannel);
    }

    private void broadcast(ChatMessage message, SocketChannel sender) throws IOException {

        byte[] data = serializeMessage(message);

        for (SocketChannel client: new ArrayList<>(clients)) {

            if (client == sender) {
                continue;
            }

            try {

                ByteBuffer buffer = ByteBuffer.allocate(data.length);
                buffer.put(data);
                buffer.flip();

                client.write(buffer);

            } catch (IOException e) {
                System.out.println("Could not send to a client: " + e.getMessage());
                closeClient(client);
            }
        }
    }

    public void disconnectClient(SelectionKey key) {

        if (key == null || !(key.channel() instanceof SocketChannel clientChannel)) {
            return;
        }

        key.cancel();
        closeClient(clientChannel);
    }

    private void closeClient(SocketChannel clientChannel) {

        clients.remove(clientChannel);

        try {
            clientChannel.close();
        } catch (IOException e) {
            System.out.println("Could not close the client: " + e.getMessage());
        }

        System.out.println("Client left, total clients: " + clients.size());
    }

    private byte[] serializeMessage(ChatMessage message) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(message);
        }

        return byteStream.toByteArray();
    }

    private ChatMessage deserializeMessage(byte[] data) {

        try (ObjectInputStream objectStream = new ObjectInputStream(new ByteArrayInputStream(data))) {

            objectStream.setObjectInputFilter(ONLY_CHAT_MESSAGE);

            Object object = objectStream.readObject();

            if (!(object instanceof ChatMessage message)) {
                throw new ChatServerException("This is not a ChatMessage object!");
            }

            return message;

        } catch (IOException | ClassNotFoundException e) {
            throw new ChatServerException("Could not read the message: " + e.getMessage());
        }
    }

    private void writeToLog(ChatMessage message) {

        try (BufferedWriter writer = Files.newBufferedWriter(
                LOG_FILE, StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {

            writer.write(message.getSenderName() + ": " + message.getContent());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Could not write the log file: " + e.getMessage());
        }
    }

    public void saveHistory() {

        try (ObjectOutputStream objectStream = new ObjectOutputStream(
                Files.newOutputStream(HISTORY_FILE))) {

            objectStream.writeObject(chatHistory);

            System.out.println("History saved: " + chatHistory.size() + " message(s)");

        } catch (IOException e) {
            System.out.println("Could not save the history: " + e.getMessage());
        }
    }

    public void loadHistory() {

        try (ObjectInputStream objectStream = new ObjectInputStream(
                Files.newInputStream(HISTORY_FILE))) {

            List<ChatMessage> oldMessages = (List<ChatMessage>) objectStream.readObject();

            chatHistory.addAll(oldMessages);

            System.out.println("History loaded: " + chatHistory.size() + " message(s)");

            for (ChatMessage message: chatHistory) {
                System.out.println("  " + message);
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load the history, starting empty: " + e.getMessage());
        }
    }

}

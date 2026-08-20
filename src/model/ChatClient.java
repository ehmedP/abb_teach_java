package src.model;

import src.exception.ChatServerException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.Scanner;
import java.util.UUID;

public class ChatClient {

    private static final String HOST = "localhost";
    private static final int PORT = 5000;
    private static final int BUFFER_SIZE = 4096;

    private static final ObjectInputFilter ONLY_CHAT_MESSAGE =
            ObjectInputFilter.Config.createFilter("src.model.ChatMessage;java.lang.String;!*");

    private final String senderName;

    private final String sessionToken = UUID.randomUUID().toString();

    public ChatClient(String senderName) {
        this.senderName = senderName;
    }

    static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.print("Your name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            name = "anonim";
        }

        new ChatClient(name).start(scanner);
    }

    public void start(Scanner scanner) {

        try (SocketChannel channel = SocketChannel.open(new InetSocketAddress(HOST, PORT))) {

            System.out.println("Connected to the server. Write 'exit' to leave.");

            Thread readerThread = new Thread(() -> listen(channel));
            readerThread.setDaemon(true);
            readerThread.start();

            while (true) {

                String line = scanner.nextLine();

                if (line.isBlank()) {
                    continue;
                }

                if ("exit".equalsIgnoreCase(line.trim())) {
                    break;
                }

                ChatMessage message = new ChatMessage(
                        senderName, line, System.currentTimeMillis(), sessionToken);

                send(channel, message);
            }

        } catch (IOException e) {
            throw new ChatServerException("Client error: " + e.getMessage());
        }
    }

    private void send(SocketChannel channel, ChatMessage message) throws IOException {

        byte[] data = serialize(message);

        ByteBuffer buffer = ByteBuffer.allocate(data.length);
        buffer.put(data);
        buffer.flip();

        channel.write(buffer);
    }

    private void listen(SocketChannel channel) {

        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

        try {

            while (true) {

                buffer.clear();

                int readCount = channel.read(buffer);

                if (readCount == -1) {
                    System.out.println("The server closed the connection.");
                    break;
                }

                buffer.flip();

                byte[] data = new byte[buffer.remaining()];
                buffer.get(data);

                ChatMessage message = deserialize(data);

                System.out.println(message.getSenderName() + ": " + message.getContent());
            }

        } catch (IOException | ChatServerException e) {
            System.out.println("Connection lost: " + e.getMessage());
        }
    }

    private byte[] serialize(ChatMessage message) throws IOException {

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        try (ObjectOutputStream objectStream = new ObjectOutputStream(byteStream)) {
            objectStream.writeObject(message);
        }

        return byteStream.toByteArray();
    }

    private ChatMessage deserialize(byte[] data) {

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
}

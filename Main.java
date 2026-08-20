import src.model.ChatServer;

import java.util.Scanner;

void main() {

    ChatServer server = new ChatServer();

    server.loadHistory();

    server.start();
}

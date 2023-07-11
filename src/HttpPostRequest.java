import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class HttpPostRequest {
    public static void main(String[] args) {
        System.out.println("@harmesm");

        System.out.println("Введите токен(сервисный ключ)");
        Scanner tokenScan = new Scanner(System.in);
        String token = tokenScan.nextLine();

        try {
            //читаем файл
            Scanner scanner = new Scanner(new File("list.txt"));
            while (scanner.hasNextLine()) {
                String tmp = scanner.nextLine();
                String pubList = tmp.substring(15);

                // Создаем URL-адрес для запроса
                URL url = new URL("https://api.vk.com/method/groups.getMembers?");

                // Создаем объект HttpURLConnection и настраиваем его
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);

                // Создаем тело запроса
                String body = "group_id=" + pubList + "&access_token="+ token +"&v=5.131 HTTP/1.1";
                String publicName = body.substring(body.indexOf("=") + 1, body.indexOf("&"));

                // Записываем тело запроса в поток вывода
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(body);
                writer.flush();

                // Считываем ответ от сервера
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Выводим ответ от сервера
                String temp = response.toString();
                if (temp.contains("group hide members")){
                    System.out.printf("%-40s%10s%n", publicName,"group hide members");
                }else {
                    System.out.printf("%-40s%10s%n", publicName,response.substring(21, response.indexOf(",")));
                }
                Thread.sleep(100);
            }
            scanner.close();
            int c = System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
package app;

import commands.*;
import data.*;
import java.io.*;
import java.util.TreeSet;

/**
 * Main - главный класс
 * @author Ershova Anna
 */
public class Main {
    private static TreeSet<Labwork> labworks = new TreeSet<Labwork>();


    public static void main (String[] args) {
        try {
            BufferedReader inputReader = null;
            String f;
            File file =null;
            boolean flag = true;
            while (flag) {
                System.out.println("Введите название файла");
                System.out.print("> ");
                inputReader = new BufferedReader(new InputStreamReader(System.in)); //?? Нужно ли
                f = inputReader.readLine();
                if (f.equals("")) {
                    System.out.println("Необходимо ввести имя файла");
                    continue;
                }
                file = new File(f);
                if (!file.exists()) {
                    System.out.println("Неверно введено имя файла");
                } else {
                    if (file.length() == 0) {
                        System.out.println("Вы ввели название пустого файла");
                    } else flag = false;
                }
            }
            Asker asker = new Asker(labworks, inputReader);
            CollectionManager collectionManager = new CollectionManager(labworks, file);
            FileRecorder fileRecorder = new FileRecorder(collectionManager.getCollection());
            fileRecorder.makeTreeSetFromFile(file);
            CommandManager commandManager = new CommandManager(new Add(collectionManager, asker),
                    new AddIfMin(collectionManager, asker),
                    new Clear(collectionManager), new ExecuteScript(),
                    new FilterContainsName(collectionManager), new Help(), new Info(collectionManager),
                    new MinByID(collectionManager), new PrintDescending(collectionManager),
                    new RemoveByID(collectionManager), new RemoveGreater(collectionManager, asker),
                    new RemoveLower(collectionManager, asker), new Save(collectionManager),
                    new Show(collectionManager), new Update(collectionManager, asker), new Exit());
            Output output = new Output(commandManager, inputReader, asker);
            output.interactiveMode();
       } catch (FileNotFoundException e) {
            System.out.println("Неверно введено имя файла");
       } catch (IOException e) {
            System.out.println("Ошибка ввода");
        }


    }

}
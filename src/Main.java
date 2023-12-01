import java.time.LocalDateTime;
import java.util.DoubleSummaryStatistics;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

/**
 * Класс Main представляет собой программу для управления антикафе, использующую классы VisitService и TableService
 * Позволяет выполнять различные действия, такие как занятие и освобождение столиков, просмотр информации о столиках, визитах и прибыли, просмотр статистических данных
 */
public class Main {
    /**
     * Представляет собой меню для пользователя антикафе
     */
    private static String menu = """

            1. Занять столик
            2. Освободить столик
            3. Просмотреть занятые столики
            4. Просмотреть свободные столики
            5. Посмотреть, сколько минут сидят за каждым столиком
            6. Посмотреть, сколько гостям нужно заплатить (конкретным)
            7. Посмотреть, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе
            8. Сколько всего заработанно
            9. Сколько в среднем занят столик по времени
            10. Узнать, какой столик чаще всего выбирается
            11. Узнать, какой столик больше всего принес в кассу
            12. Получить список всех визитов
            13. Получить список всех завершенных визитов
            """;
    private static Scanner in = new Scanner(System.in);

    /**
     * Основной блок программы, управляющий выполнением действий пользователя в антикафе
     *
     * @param args аргументы командной строки (не используются)
     */
    public static void main(String[] args) {

        while (true) {
            System.out.println("\nПриветствуем Вас в нашем Антикафе!");
            System.out.println(menu);
            System.out.println("Выберите действие: ");
            String optionLine = in.nextLine();
            String tableIdLine;
            int option = 0;
            int tableId;
            option = toDigit(optionLine);


            switch (option) {
                case 1 -> {
                    System.out.println("Список свободных столиков: ");
                    for (Table table : VisitService.getFreeTables()) {
                        System.out.println(table);
                    }
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    try {
                        Visit visit = VisitService.createVisit(new Client(), tableId);
                        System.out.printf("Столик успешно занят.%n ID визита: %d%nСтарт визита: %s", visit.getId(), visit.getFormattedTime());
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }
                }
                case 2 -> {
                    System.out.println("Занятые столики");
                    System.out.println(VisitService.getReservedTables());
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);

                    try {
                        VisitService.finishVisit(tableId);
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }
                    System.out.println(VisitService.getAverageDurationOfAllTables());

                }
                case 3 -> {
                    System.out.println("Список занятых столиков: ");
                    for (Table table : VisitService.getReservedTables()) {
                        System.out.println(table);
                    }

                }
                case 4 -> {
                    System.out.println("Список свободных столиков");
                    for (Table table : VisitService.getFreeTables()) {
                        System.out.println(table);
                    }
                }
                case 5 -> {
                    System.out.println("Просмотр информации о том, сколько всего минут сидят за каждым столом");
                    System.out.println(VisitService.getTotalCurrentDuration());

                }
                case 6 -> {
                    System.out.println("Выберите столик: ");
                    tableIdLine = in.nextLine();
                    tableId = toDigit(tableIdLine);
                    System.out.println();
                    for (Map.Entry<Table, Long> entry :
                            VisitService.getTotalCurrentDuration().entrySet()) {
                        System.out.println(entry.getKey() + " : " + entry.getValue() + " минут");
                    }
                    System.out.println();
                    try {
                        System.out.println(VisitService.getCurrentCost(tableId) + " рублей");
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                case 7 -> {
                    System.out.println("Просмотр информации о том, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе");
                    System.out.println(VisitService.getTotalCurrentDuration());
                    System.out.println(VisitService.getTotalCurrentCost());
                }
                case 8 -> {
                    System.out.println("Общая прибыль");
                    System.out.println(VisitService.getTotalCostOfAllTime());
                }
                case 9 -> {
                    System.out.println("Cредняя занятость столиков по времени");
                    Map<Table, DoubleSummaryStatistics> map = VisitService.getAverageDurationOfAllTables();

                    for (Table table : map.keySet()
                    ) {
                        System.out.println(table + ": " + map.get(table).getAverage() + " минут");
                    }
                }
                case 10 -> {
                    System.out.println("Столик, который чаще всего выбирается");
                    try {
                        System.out.println(VisitService.getTheMostPopularTable());
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                case 11 -> {
                    try {
                        System.out.println(VisitService.getTheMostEarnedTable());
                    } catch (RuntimeException ex) {
                        System.out.println(ex.getMessage());
                    }

                }
                case 12 -> {
                    System.out.println("Cписок всех визитов");
                    for (Visit visit : VisitService.getVisits()) {
                        System.out.printf("Визит №%d%n    Столик: %s%n    Длительность: %dминут%n    Занятость: %s  %n", visit.getId(), visit.getTable(), visit.getDuration(), visit.isFinished() ? "Завершен" : "Не завершен");
                        if (visit.isFinished())
                            System.out.printf("    Стоимость: %.2f%n", visit.getCost());
                    }
                }
                case 13 -> {
                    System.out.println("Список всех завершенных визитов");
                    for (Visit visit : VisitService.getFinishedVisits()) {
                        System.out.printf("Столик: %s%n Длительность: %d Занятость: %b  ", visit.getTable(), visit.getDuration(), visit.isFinished());
                        if (visit.isFinished())
                            System.out.printf("Стоимость: %f", visit.getCost());

                    }
                }
                default -> {
                    System.out.println("Вы должны ввести число!");
                }
            }

        }

    }

    public static int toDigit(String line) {
        return StringUtils.isNumeric(line)? Integer.parseInt(line):0;
    }



}
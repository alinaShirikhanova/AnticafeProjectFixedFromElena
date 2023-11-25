import java.util.DoubleSummaryStatistics;
import java.util.Map;
import java.util.Scanner;

public class Main {
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

    public static void main(String[] args) {

        while (true) {
            System.out.println("\nПриветствуем Вас в нашем Антикафе!");
            System.out.println(menu);
            System.out.println("Выберите действие: ");
            int option = in.nextInt();
            switch (option) {
                case 1 -> {
                    System.out.println("Список свободных столиков: ");
                    for (Table table :TableService.getFreeTables()) {
                        System.out.println(table);
                    }
                    System.out.println("Выберите столик: ");
                    int tableId = in.nextInt();
                    Visit visit = VisitService.createVisit(new Client(), tableId);
                    System.out.printf("Столик успешно занят.%n ID визита: %d%nСтарт визита: %s", visit.getId(), visit.getFormattedTime() );
                }
                case 2 -> {
                    System.out.println("Выберите столик: ");

                    int tableId = in.nextInt();
                    VisitService.finishVisit(tableId);
                    System.out.println(VisitService.getAverageDurationOfAllTables());

                }
                case 3 -> {
                    System.out.println("Список занятых столиков: ");
                    for (Table table :VisitService.getReservedTables()){
                        System.out.println(table);
                    }

                }
                case 4 -> {
                    System.out.println("Список свободных столиков");
                    for (Table table :VisitService.getFreeTables()){
                        System.out.println(table);
                    }
                }
                case 5 ->{
                    System.out.println("Просмотр информации о том, сколько всего минут сидят за каждым столом");
                    System.out.println(VisitService.getTotalCurrentDuration());

                }
                case 6 -> {
                    System.out.println("Выберите столик: ");
                    int tableId = in.nextInt();
                    System.out.println();
                    System.out.println(VisitService.getTotalCurrentDuration());
                    System.out.println(VisitService.getCurrentCost(tableId));
                }
                case 7 -> {
                    System.out.println("Просмотр информации о том, сколько придется заплатить всем гостям за столиками, если они прямо сейчас покинут антикафе");
                    System.out.println(VisitService.getTotalCurrentDuration());
                    System.out.println(VisitService.getTotalCurrentCost());
                }

                case 8 ->{
                    System.out.println("Общая прибыль");
                    System.out.println(VisitService.getTotalCostOfAllTime());
                }
                case 9 -> {
                    System.out.println("");
                    Map<Table, DoubleSummaryStatistics> map = VisitService.getAverageDurationOfAllTables();

                    for (Table table:map.keySet()
                    ) {
                        System.out.println(table + ": " + map.get(table));
                    }
                }
                case 10 -> {
                    System.out.println("Чаще всего выбирается");
                    System.out.println(VisitService.getTheMostPopularTable());
                }
                case 11 -> {
                    System.out.println("больше всего в кассу");
                    System.out.println(VisitService.getTheMostEarnedTable());
                }
                case 12 -> {
                    for (Visit visit:VisitService.getVisits()) {
                        System.out.printf("Столик: %s%n Длительность: %d Занятость: %b  ", visit.getTable(), visit.getDuration(), visit.isFinished());
                        if (visit.isFinished())
                            System.out.printf("Стоимость: %f", visit.getCost());

                    }
                }

                case 13 -> {
                    for (Visit visit:VisitService.getFinishedVisits()) {
                        System.out.printf("Столик: %s%n Длительность: %d Занятость: %b  ", visit.getTable(), visit.getDuration(), visit.isFinished());
                        if (visit.isFinished())
                            System.out.printf("Стоимость: %f", visit.getCost());

                    }
                }
            }

        }

    }
}
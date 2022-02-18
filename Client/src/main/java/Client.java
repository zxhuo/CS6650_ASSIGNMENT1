import java.sql.Timestamp;
import java.util.concurrent.CountDownLatch;

public class Client {

    private static final int MAX_THREADS = 256;
    private static final int MAX_SKIERS = 50000;
    private static final int MIN_LIFTS = 5;
    private static final int MAX_LIFTS = 60;
    private static final int MAX_RUNS = 20;
    private static final String DEFULT_SEASON_ID = "2022";
    private static final String DEFULT_DAY_ID = "1";
    private static final int DEFULT_RESORT_ID = 1;

    private static int numThreads;
    private static int numSkiers;
    private static int numLifts;
    private static int numRuns;
    private static int port;

    public static void main(String[] args) {
        if(!isValidArgs(args)){
            return;
        }
        int numP1Thread = (int)Math.round(numThreads/4);
        int numP3Thread = (int)Math.round(numThreads/10);
        StatusBoard board = new StatusBoard();
        CountDownLatch totalThread = new CountDownLatch(numP1Thread+numThreads+numP3Thread);

        PhaseOne p_one = new PhaseOne(numP1Thread, numSkiers, numRuns,1, 90,
                numLifts,DEFULT_SEASON_ID,DEFULT_DAY_ID,DEFULT_RESORT_ID,
                (int) Math.round(numP1Thread/5), totalThread, board);
        PhaseTwo p_two = new PhaseTwo(numThreads,numSkiers, numRuns,91, 360,
                numLifts,DEFULT_SEASON_ID,DEFULT_DAY_ID,DEFULT_RESORT_ID,
                (int) Math.round(numP1Thread/5),totalThread, board);
        PhaseThree p_three = new PhaseThree(numP3Thread, numSkiers, numRuns,361, 420,
                numLifts,DEFULT_SEASON_ID,DEFULT_DAY_ID,DEFULT_RESORT_ID,
                totalThread, board);

        try {
            Timestamp startTime = new Timestamp(System.currentTimeMillis());
            p_one.run();
            p_two.run();
            p_three.run();
            totalThread.await();
            Timestamp endTime = new Timestamp(System.currentTimeMillis());
            long wallTime = endTime.getTime() - startTime.getTime();
            System.out.println("suc "+board.getSucReq());
            System.out.println("unsuc "+board.getUnsucReq());
            System.out.println("req "+board.getTotalReq());
            System.out.println("WALL TIME: " + wallTime);
            System.out.println("throughput: " +(int)board.getTotalReq()/((double)wallTime/1000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidArgs(String[] args) {
        if(args.length != 5){
            System.out.println("MISS ARGS");
            return false;
        }else{
            try{
                numThreads = Integer.parseInt(args[0]);
                numSkiers = Integer.parseInt(args[1]);
                numLifts = Integer.parseInt(args[2]);
                numRuns = Integer.parseInt(args[3]);
                port = Integer.parseInt(args[4]);
                if (numThreads > MAX_THREADS) {
                    System.out.println("OVER MAX THREADS");
                    return false;
                }
                if (numSkiers > MAX_SKIERS) {
                    System.out.println("OVER MAX SKIERS");
                    return false;
                }
                if (numLifts < MIN_LIFTS || numLifts > MAX_LIFTS) {
                    System.out.println("LIFTS NOT IN THE RANGE");
                    return false;
                }
                if (numRuns > MAX_RUNS) {
                    System.out.println("OVER MAX RUNS");
                    return false;
                }
                return true;
            }catch (Exception e){
                System.out.println("WRONG INPUTS");
                return false;
            }
        }
    }
}

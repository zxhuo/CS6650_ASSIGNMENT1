public class StatusBoard {

    private int sucReq;
    private int unsucReq;
    private int totalReq;

    public StatusBoard() {
        this.sucReq = 0;
        this.unsucReq = 0;
        this.totalReq = 0;

    }

    public synchronized void addSucReq(int n){
        sucReq += n;
    }

    public synchronized void addUnsucReq(int n){
        unsucReq += n;
    }

    public synchronized void addTotalReq(int n){
        totalReq += n;
    }

    public int getTotalReq(){
        return totalReq;
    }

    public int getSucReq() {
        return sucReq;
    }

    public int getUnsucReq() {
        return unsucReq;
    }
}

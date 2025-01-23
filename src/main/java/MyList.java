import java.util.ArrayList;

public class MyList {
    ArrayList<String> list;
    public MyList() {
        this.list = new ArrayList<>();
    }

    public void add(String item) {
        this.list.add(item);
    }

    @Override
    public String toString() {
        StringBuilder listString = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            listString.append(i + 1).append(". ").append(list.get(i)).append("\n");
        }
        return listString.toString();
    }
}

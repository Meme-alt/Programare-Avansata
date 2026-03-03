package Lab2;

public class Main {
    public static void main(String[] args){
        Location l = new airport("Tatarasi", 10, 25, 5);
        System.out.println(l.getName() + " "  + l.getXcord() + " " + l.getYcord());
        Location l2 = new city("Iasi", 12, 24, 400000);
        Road r = new Road("Stejar", Road.Roadtype.HIGHWAY, 10, 100, l, l2);
        System.out.println(r.getType()+ " " + r.getLength() + " " + r.getSpeedLimit());
        l.setXcord(5);
        System.out.println(l.getXcord());
        r.setLength(30);
        System.out.println(r.getLength());
    }
}

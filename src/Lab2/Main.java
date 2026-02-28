package Lab2;

public class Main {
    public static void main(String[] args){
        Location l = new Location("Tatarasi", 10, 25);
        System.out.println(l.getName() + " "  + l.getXcord() + " " + l.getYcord());
        Road r = new Road("Stejar", Road.Roadtype.HIGHWAY, 10, 100);
        System.out.println(r.getType()+ " " + r.getLength() + " " + r.getSpeedLimit());
        l.setXcord(5);
        System.out.println(l.getXcord());
        r.setLength(30);
        System.out.println(r.getLength());
    }
}

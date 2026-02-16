public class Homework {
    public static void main(String[] args){
        if(args.length < 2){
            System.out.println("Less than 2 arguments");
        }
        int n = Integer.parseInt(args[0]);
        String type = args[1];
        long starttime = System.currentTimeMillis();
        int m[][] = new int[n][n];
        int white = 255;
        int black = 0;
        if(type.equals("rectangle")) {
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i >= n/4 && i <= 2*n/4 && j >= n/4 && j <= 2*n/4) {
                        m[i][j] = black;
                    } else {
                        m[i][j] = white;
                    }
                }
            }
            String f = Img(m);
            //System.out.println(f);
        }
        else if(type.equals("circle")){
            int c[][] = new int[n][n];
            int x = n / 2;
            int y = n / 2;
            int r = n / 3;
            int r2 = r * r;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int d = (i - y) * (i - y) + (j - x) * (j - x);
                    if (d <= r2) {
                        m[i][j] = white;
                    } else {
                        m[i][j] = black;
                    }
                }
            }
            String p = Img(m);
            //System.out.println(p);
        }
        else{
            System.out.println("No known type");
            return;
        }
        Box(m);
        long endtime = System.currentTimeMillis();
        long finaltime = endtime - starttime;
        System.out.println("Time: " + finaltime);
    }
    public static String Img(int[][] m){
        String image = "";
        char block = (char) 219;
        char block2 = (char) 176;
        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m[i].length; j++){
                if(m[i][j] == 0){
                    image += block + "" + block;
                }
                else{
                    image += block2 + "" + block2;
                }
            }
            image += "\n";
        }
        return image;
    }
    public static void Box(int[][] m){
        int minr = m.length;
        int minc = m.length;
        int maxr = -1;
        int maxc = -1;
        int background = m[0][0];
        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m.length; j++){
                if(m[i][j] != background){
                    if(i < minr) minr = i;
                    if(i > maxr) maxr = i;
                    if(j < minc) minc = j;
                    if(j > maxc) maxc = j;
                }
            }
        }
        System.out.println("Top left corner is: " + minr + ", " + minc + "\n");
        System.out.println("Bottom Left corner is: " + maxr + ", " + minc + "\n");
        System.out.println("Top right corner is: " + minr + ", " + maxc + "\n");
        System.out.println("Bottom right corner is: " + maxr + ", " + maxc + "\n");
    }
}

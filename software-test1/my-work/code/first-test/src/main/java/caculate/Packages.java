package caculate;

public class Packages {
    private double package_length;//长
    private double package_width;//宽
    private double package_height;//高
    private double package_weight;//高


    public Packages(double package_length, double package_width, double package_height, double package_weight) {
        this.package_length=package_length;
        this.package_width=package_width;
        this.package_height=package_height;
        this.package_weight=package_weight;
    }

    public double To_length(){
        return package_length;
    }

    public double To_width(){
        return package_width;
    }

    public double To_height(){
        return package_height;
    }

    public double To_weight(){
        return package_weight;
    }
}

import java.util.*;

public class dataVariables {
  
  private double weight;
  private int units;
  private String originCluster;
  private double originClusterLat;
  private double originClusterLong;
  private double originLat;
  private double originLong;
  private String destinationCluster;
  private double destinationClusterLat;
  private double destinationClusterLong;
  private double destinationLat;
  private double destinationLong;
  private double volume;
 
  public dataVariables (double weight,int units,String originCluster,double originClusterLat, double originClusterLong,
                double originLat,double originLong,String destinationCluster,double destinationClusterLat,
                double destinationClusterLong, double destinationLat, double destinationLong, double volume) {
    this.weight =weight;
    this.units=units;
    this.originCluster = originCluster;
    this.originClusterLat = originClusterLat;
    this.originClusterLong = originClusterLong;
    this.originLat=originLat;
    this.originLong=originLong;
    this.destinationCluster = destinationCluster;
    this.destinationClusterLat = destinationClusterLat; 
    this.destinationClusterLong = destinationClusterLong;
    this.destinationLat = destinationLat;
    this.destinationLong=destinationLong;
    this.volume=volume;
  }
  public double getWeight(){
    return this.weight;
  }
  public int getUnits(){
  return this.units;
  }
  public String getOriginCluster(){
    return this.originCluster;
  }
  public String getDestinationCluster(){
    return this.destinationCluster;
  }
  public double getVolume(){
    return this.volume;
  }
  public double getOriginClusterLat(){
    return this.originClusterLat;
  }
  public double getOriginClusterLong(){
    return this.originClusterLong;
  }
  public double getOriginLat(){
    return this.originLat;
  }
  public double getOriginLong(){
    return this.originLong;
  }
  public double getDestinationClusterLat(){
    return this.destinationClusterLat;
  }
  public double getDestinationClusterLong(){
    return this.destinationClusterLong;
  }
  public double getDestinationLat(){
    return this.destinationLat;
  }
  public double getDestinationLong(){
    return this.destinationLong;
  }
}
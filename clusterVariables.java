import java.util.*;

public class clusterVariables {
  
  private String originCluster;
  private double originClusterLat;
  private double originClusterLong;
  private double originLat;
  private double originLong;
 
  public clusterVariables (String originCluster,double originClusterLat, double originClusterLong, double originLat,double originLong){
   
    this.originCluster = originCluster;
    this.originClusterLat = originClusterLat;
    this.originClusterLong = originClusterLong;
    this.originLat=originLat;
    this.originLong=originLong;
  }

  public String getOriginCluster(){
    return this.originCluster;
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
 }
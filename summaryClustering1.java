import java.util.*;
import java.io.*;
import java.util.Scanner;

public class summaryClustering1 {
  
  public static void main(String[] args) {
    String filename = "TRsSelectedDataUM.txt";
    dataVariables[] shipments =null;
    try{
      shipments = readArray(filename);
    }
    catch (FileNotFoundException e){
      e.printStackTrace();
    }
    
    double totalWeight = totalWeight(shipments);
    double totalVolume = totalVolume(shipments);
    double totalNumberOfShipments = totalNumberOfShipments(shipments);
    int totalDistance = totalDistanceInfo(shipments);
    int totalDistance2 = totalDistance2Info(shipments);
    
    System.out.println("Statistics of total data:");
    System.out.println("Total Weight = "+totalWeight);
    System.out.println("Total Volume = "+totalVolume);
    System.out.println("Total Number of Shipments = "+totalNumberOfShipments);
    System.out.println("Total distance of all shipmentss to their origin cluster locations = "+totalDistance);
    System.out.println("Total distance of all shipmentss to their detination cluster locations = "+totalDistance2);
    
    double averageWeight = (totalWeight/648);
    double averageNumberOfShipments = (totalNumberOfShipments/648);
    double averageVolume = (totalVolume/648);
    double averageDistance = (totalDistance/648);
    double averageDistance2 = (totalDistance2/648);
    
    System.out.println("\n" + "Statistics of average cluster:");
    System.out.println("Average Weight = "+averageWeight);
    System.out.println("Average Volume = "+averageVolume);
    System.out.println("Average Number of Shipments = "+averageNumberOfShipments);
    System.out.println("Average distance of all shipmentss to their origin cluster locations = "+averageDistance);
    System.out.println("Average distance of all shipmentss to their detination cluster locations = "+averageDistance2); 
    
    double[] minValueWeight = lowestValues(shipments, 1);
    double[] minValueVolume = lowestValues(shipments, 2);
    double[] minValueUnits = lowestValues(shipments, 3);
    
    System.out.println("\n" + "Statistics of 3 lowest cluster datas");
    System.out.println("Lowest Weight = "+ Arrays.toString(minValueWeight));
    System.out.println("Lowest Volume = "+Arrays.toString(minValueVolume));
    System.out.println("Lowest Number of Shipments = "+Arrays.toString(minValueUnits));
    
    double[] maxValueWeight = highestValues(shipments, 1);
    double[] maxValueVolume = highestValues(shipments, 2);
    double[] maxValueUnits = highestValues(shipments, 3);
    
    System.out.println("\n" + "Statistics of 3 highest cluster datas");
    System.out.println("Highest Weight = "+ Arrays.toString(maxValueWeight));
    System.out.println("Highest Volume = "+Arrays.toString(maxValueVolume));
    System.out.println("Highest Number of Shipments = "+Arrays.toString(maxValueUnits));
  }
  
  public static double totalWeight(dataVariables[] shipments){
    double a =0;
    double b =0;
   for(int i=0; i<81520; i++){
    a = shipments[i].getWeight(); 
    b = b + a;   
   }
    return b;
  }
  
  public static double totalVolume(dataVariables[] shipments){
    double a=0;
    double b=0;
    for(int i=0; i<81520; i++){
    a = shipments[i].getVolume(); 
    b = b + a;   
   }
    return b;
  }
  
  public static double totalNumberOfShipments(dataVariables[] shipments){
    double a=0;
    double b=0;
    for(int i=0; i<81520; i++){
    a = shipments[i].getUnits(); 
    b = b + a;  
   }
    return b;
  }
  
  //The method below goes through all the data in the .txt file, by using the java method next. The dataVariablesrmation will be saved in an array called Shipment.
  public static dataVariables[] readArray(String filename) throws java.io.FileNotFoundException{
    File file = new File(filename);
    Scanner input = new Scanner (file);
    
    dataVariables[] Shipment = new dataVariables[81520];
    
    for (int i =0; i<81520;i++){
      double weight=input.nextDouble();
      int units =input.nextInt();
      String originCluster=input.next();
      double originClusterLat =input.nextDouble();
      double originClusterLong=input.nextDouble();
      double originLat=input.nextDouble();
      double originLong=input.nextDouble();
      String destinationCluster = input.next();
      double destinationClusterLat=input.nextDouble();
      double destinationClusterLong =input.nextDouble();
      double destinationLat=input.nextDouble();
      double destinationLong =input.nextDouble();
      double volume=input.nextDouble();
      
      dataVariables a = new dataVariables(weight, units, originCluster, originClusterLat, originClusterLong, 
                                          originLat, originLong, destinationCluster,destinationClusterLat,
                                          destinationClusterLong,destinationLat, destinationLong,volume);
      Shipment[i]=a;
    }
    input.close();
    return Shipment;
  }
  
  //The distance between 2 locations will be calculated by using the Haversine formulas
    private static double distance(double latA, double longA, double latB, double longB) {
      double theta = longA - longB;
      double distance = Math.sin(degreeRadian(latA)) * Math.sin(degreeRadian(latB)) + Math.cos(degreeRadian(latA)) * Math.cos(degreeRadian(latB)) * Math.cos(degreeRadian(theta));
      distance = Math.acos(distance);
      distance = radianDegree(distance);
      distance = distance * 60 * 1.1515;
      distance = distance * 1.609344;
      return (distance);
    }
   
  //Converting decimal degrees to radians
  private static double degreeRadian(double deg) {
    return (deg * Math.PI / 180.0);
  }
  
  //Converting radians to decimal degrees
  private static double radianDegree(double rad) {
    return (rad * 180.0 / Math.PI);
  }
  
  //Total distance from Origin Location to Origin Cluster
   public static int totalDistanceInfo(dataVariables[] shipments){
    int totalDistance = 0;
    for(int i=0;i<81520;i++){
      int a = (int)totalDistance +  (int)distance(shipments[i].getOriginLat(),shipments[i].getOriginLong(),shipments[i].getOriginClusterLat(),shipments[i].getOriginClusterLong());
      totalDistance=a;
    }
    return totalDistance;
  }
   
   //Total distance from Destinantion Location to Destination Cluster
    public static int totalDistance2Info(dataVariables[] shipments){
    int totalDistance = 0;
    for(int i=0;i<81520;i++){
      int a = (int)totalDistance +  (int)distance(shipments[i].getDestinationLat(),shipments[i].getDestinationLong(),shipments[i].getDestinationClusterLat(),shipments[i].getDestinationClusterLong());
      totalDistance=a;
    }
    return totalDistance;
  }
    
    //Below you can find all methods to calculate the minimum values 
   public static double[] lowestValues(dataVariables[] shipments, int variable){
      double minValue1;
      double minValue2;
      double minValue3;
      switch(variable){
        case 1:
          minValue1 = shipments[1].getWeight();
          minValue2 = shipments[1].getWeight();
          minValue3 = shipments[1].getWeight();
          break;
        case 2:
          minValue1 = shipments[1].getVolume();
          minValue2 = shipments[1].getVolume();
          minValue3 = shipments[1].getVolume();
          break;
        case 3:
          minValue1 = shipments[1].getUnits();
          minValue2 = shipments[1].getUnits();
          minValue3 = shipments[1].getUnits();
          break;
        default:
          minValue1=0;
          minValue2=0;
          minValue3=0;
      }
    
    for(int i=0; i<81520; i++){
      double a = 1.0;
      switch(variable){
        case 1:
          a = shipments[i].getWeight();
          break;
        case 2:
          a = shipments[i].getVolume();
          break;
        case 3:
          a = shipments[i].getUnits();
          break;
      }
      if(a < minValue1){
        minValue3 = minValue2;
        minValue2 = minValue1;
        minValue1 = a;
      }
      else if(a < minValue2){
        minValue3 = minValue2;
        minValue2 = a;
      }
      else if(a < minValue3){
        minValue3 = a;
      } 
    }
    
   double[] minValueCluster;
   minValueCluster = new double[3];
    minValueCluster[0] = minValue1;
    minValueCluster[1] = minValue2;
    minValueCluster[2] = minValue3;
    return minValueCluster;
  }
   
   public static double[] highestValues(dataVariables[] shipments, int variable){
      double maxValue1;
      double maxValue2;
      double maxValue3;
      switch(variable){
        case 1:
          maxValue1 = shipments[1].getWeight();
          maxValue2 = shipments[1].getWeight();
          maxValue3 = shipments[1].getWeight();
        break;
        case 2:
          maxValue1 = shipments[1].getVolume();
          maxValue2 = shipments[1].getVolume();
          maxValue3 = shipments[1].getVolume();
          break;
        case 3:
          maxValue1 = shipments[1].getUnits();
          maxValue2 = shipments[1].getUnits();
          maxValue3 = shipments[1].getUnits();
          break;
        default:
          maxValue1=0;
          maxValue2=0;
          maxValue3=0;
      }
    
    for(int i=1; i<81520; i++){
      double a = 1.0;
      switch(variable){
        case 1:
          a = shipments[i].getWeight();
          break;
        case 2:
          a = shipments[i].getVolume();
          break;
        case 3:
          a = shipments[i].getUnits();
          break;
      }
      if(a > maxValue1){
        maxValue3 = maxValue2;
        maxValue2 = maxValue1;
        maxValue1 = a;
      }
      else if(a > maxValue2){
        maxValue3 = maxValue2;
        maxValue2 = a;
      }
      else if(a > maxValue3){
        maxValue3 = a;
      } 
    }
   double[] maxValueCluster;
    maxValueCluster = new double[3];
    maxValueCluster[0] = maxValue1;
    maxValueCluster[1] = maxValue2;
    maxValueCluster[2] = maxValue3;
    return maxValueCluster;
  }
}
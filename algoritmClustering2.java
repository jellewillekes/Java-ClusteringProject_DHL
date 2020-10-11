import java.util.*;
import java.io.*;
import java.util.Scanner;

public class algoritmClustering2{
  
  //Main method that declares and scans the selected data and prints
  //1a. Create k random cluster locations.
  //1b. Place k random cluster locations.
  //2.  Find closest cluster location to each point--> check for each observation in our Data file which random clusterpoint is closest and adds it to the cluster.
  //3.  Calculate average of points (lat, long) of each cluster and sets the origin of the cluster to the average points (lat, long).
  //4.  Repeat process of creating clusters and finding averages.
  //5.  In case there are no shipments closest to a specific cluster (See count fuction in updating method), the random cluster will be assigned to a random locaiton again 
  //    such that the total distance will be smaller as there will be new closest shipments to this new random cluster location.
  //6.  Prints total distance for given number of iterations.
  //7.  New optimal cluster locations will be printed too.
  
  public static void main(String[] args){
    String filename = "TRsSelectedDataUM.txt";
    dataVariables[] shipments =null;
    try{
      shipments = readArray(filename);
    }
    catch (FileNotFoundException e){
      e.printStackTrace();
    }
    System.out.println("How many clusters?");
    Scanner inputField3 = new Scanner(System.in);
    int k = inputField3.nextInt();
    inputField3.close();
    
    double[][] randomClusterLocation = new double[k][2]; 
    randomClusterLocation = createClusterLocation(k);                          //1b. In the main it places k new cluster locations.  
    
    for(int i=0; i<k; i++){
      System.out.println("Latitude + Longitude of random Cluster" + (i + 1));
      for(int j=0;j<2;j++){
        System.out.println(randomClusterLocation[i][j]);                       //Prints longitude and latitude of the k new Cluster Locations
      }
    }
    
    clusterVariables[] reducedData = createClusterData(shipments);             
    clusterVariables[] adjusted = updating(randomClusterLocation,reducedData,k);
    double distanceAdjusted = totalDistanceDataVariables (adjusted);
    System.out.println("The total distance of a single observations to their new Cluster:");
    System.out.println(distanceAdjusted);
   }

//Method to do updates:
  public static clusterVariables[] updating(double[][] randomClusterLocation, clusterVariables[] reducedData,int k){
    clusterVariables[] TRsSelectedDataUM = new clusterVariables[81520];
    TRsSelectedDataUM = reducedData;
    double[][] newClusterLocation = new double [k][2];
    newClusterLocation = randomClusterLocation;
    
    for (int i=0; i<30; i++){                                      //amount of updates can be adjusted on preference.
      
      for (int j=0;j<81520;j++){                           
        double a = TRsSelectedDataUM[j].getOriginLat();
        double b= TRsSelectedDataUM[j].getOriginLong();
        int c = findClosest(newClusterLocation, a,b,k);
        String originCluster = String.valueOf(c);
        double originClusterLat = newClusterLocation[c][0];
        double originClusterLong = newClusterLocation[c][1];
        double originLat = TRsSelectedDataUM[j].getOriginLat();
        double originLong = TRsSelectedDataUM[j].getOriginLong();
        clusterVariables f = new clusterVariables(originCluster,originClusterLat,originClusterLong,originLat,originLong);
        TRsSelectedDataUM[j] =f; 
      }
      
      for(int l=0; l<k; l++){
        double totalLat =0;
        double totalLong =0;
        int count =0; 
        
        for(int m=0; m<81520; m++){                                            
          if(TRsSelectedDataUM[m].getOriginCluster().equals(String.valueOf(l))){
            totalLat = totalLat + TRsSelectedDataUM[m].getOriginLat();
            totalLong = totalLong + TRsSelectedDataUM[m].getOriginLong();
            count++;
          }  
        }
        
        if(count == 0){                                //5. In case there are no shipments closest to a specific cluster (Counts amount of shipments that are closest to the cluster).
          double latitude = 36 + 21 * Math.random();   //5. Random cluster points will be assigned to a random location points again, and the process of averaging can start again.
          double longitude = -9 + 26 * Math.random();
          newClusterLocation[l][0] = latitude;
          newClusterLocation[l][1] = longitude;
        }
        else{
          double averageLat = (totalLat/count);         //3. Calculate average of points (lat, long) of each cluster. and sets the origin of the cluster to the average points (lat, long).
          double averageLong = (totalLong/count);
          newClusterLocation[l][0] = averageLat;        //3. Set Origin (random) points of the closter to the better estimated average points (lat, long)  
          newClusterLocation[l][1] = averageLong;
        }  
      }
      
      if(i%1000 ==0){      //print it out every 100
       for(int p=0; p<k; p++){
         System.out.println("Latitude + Longitude of new Cluster" + (p + 1));
         for(int q=0; q<2; q++){
           System.out.println(newClusterLocation[p][q]);       //7. New optimal cluster locations will be printed too.
          }       
       }
    }
      System.out.println("\n"+"Total Distance at " + (i + 1) + " iterations: ");  //6.  Prints total distance for given number of iterations.
      double distanceIterated = totalDistanceDataVariables (TRsSelectedDataUM);
      System.out.println(distanceIterated);   
  }
  return TRsSelectedDataUM;     
}

//Get the values and put them in the array called dataVariables.
    public static dataVariables[] readArray(String filename)
      throws java.io.FileNotFoundException{
      File file = new File(filename);
      Scanner input = new Scanner (file);
      
      dataVariables[] shipments = new dataVariables[81520];
      
      for (int i =0; i<81520;i++){
        double weight=input.nextDouble();
        int units =input.nextInt();
        String originCluster=input.next();
        double originClusterLat =input.nextDouble();
        double originClusterLong=input.nextDouble();
        double originLat=input.nextDouble();
        double originLong    =input.nextDouble();
        String destinationCluster = input.next();
        double destinationClusterLat=input.nextDouble();
        double destinationClusterLong =input.nextDouble();
        double destinationLat=input.nextDouble();
        double destinationLong =input.nextDouble();
        double volume=input.nextDouble();
        
        dataVariables a = new dataVariables(weight,units,originCluster,originClusterLat,originClusterLong,originLat,originLong,destinationCluster,destinationClusterLat,destinationClusterLong,destinationLat,destinationLong,volume);
        shipments[i]=a;
      }
      
      input.close();
      
      return shipments;
    }
    
 //1a. Create k random new cluster locations.
    public static double[][] createClusterLocation(int k){
      double[][] randomClusterLocation = new double[k][2];
      for (int j =0; j<k; j++){
        double latitude = 36 + 21*Math.random();
        double longitude = -9 + 26*Math.random();
        randomClusterLocation[j][0] = latitude;
        randomClusterLocation[j][1] = longitude;
      }
      return randomClusterLocation;
    }

//2.  Find closest cluster location to each point--> check for each observation in our Data file which clusterpoint is closest and adds it to the cluster.
    public static int findClosest(double[][] clusterPoints, double a, double b, int k){
      int c=0;
      double i = Double.MAX_VALUE;
      for(int j=0;j<k;j++){
        double distance =0;
        distance = distance(clusterPoints[j][0],clusterPoints[j][1],a,b);
        if (distance<i){
          i=distance;
          c=j;
        }
      }
      return c;
    }
    
     private static double distance(double latA, double longA, double latB, double longB) {
      double theta = longA - longB;
      double distance = Math.sin(degreeRadian(latA)) * Math.sin(degreeRadian(latB)) + Math.cos(degreeRadian(latA)) * Math.cos(degreeRadian(latB)) * Math.cos(degreeRadian(theta));
      distance = Math.acos(distance);
      distance = radianDegree(distance);
      distance = distance * 60 * 1.1515;
      distance = distance * 1.609344;
      return (distance);
    }
 
//Creates Cluster Data
    public static clusterVariables[] createClusterData(dataVariables[] shipments){
      clusterVariables[] clustered = new clusterVariables[81520];
      clusterVariables[] wrong = new clusterVariables[81520];
      for (int i =0; i<81520; i++){
        String originCluster = "Cluster1";
        double originClusterLat = shipments[i].getOriginClusterLat();
        double originClusterLong = shipments[i].getOriginClusterLong();
        double originLat = shipments[i].getOriginLat();
        double originLong = shipments[i].getOriginLong();
        clusterVariables a = new clusterVariables(originCluster,originClusterLat,originClusterLong,originLat,originLong);
        clustered[i] =a; 
        if (i==81519){
          return clustered;
        }
      }
      return wrong;
    }
    
//Dist between Origin Dest - Origin Cluster
    public static int totalDistanceDataVariables(clusterVariables[] shipments){
      int totalDistance = 0;
      int a=0;
      for(int i=0;i<81520;i++){
        a = (int)distance(shipments[i].getOriginLat(), shipments[i].getOriginLong(), shipments[i].getOriginClusterLat(), shipments[i].getOriginClusterLong());
        totalDistance= totalDistance + a;
      }
      return totalDistance;
    }
    
//Convert degrees to radians
    private static double degreeRadian(double deg) {
      double radian = deg * Math.PI / 180.0;
      return (radian);
    }
    
 //Convert radians to degrees
    private static double radianDegree(double rad) {
      double degree = rad * 180.0 / Math.PI;
      return (degree);
    }
  }
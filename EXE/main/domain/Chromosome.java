package domain;
import java.util.ArrayList;
import java.util.Comparator;
import domain.Aula;
import domain.Fecha;
import java.text.DecimalFormat;
import java.util.concurrent.ThreadLocalRandom;
import domain.Turno;
public class Chromosome {
    private final int NUMBER_OF_GENES; //number of genes -> number of courses
    private final ArrayList<Gene> genes;
    private ArrayList<Integer> objectives;
    
    DecimalFormat form = new DecimalFormat("#0.00");
    double fitness = -1;
    int numberOfDominatedSolutions=-1;
    double truncDistance;//maybe remove if we transform spea2 -> spea
    
    //maybe there is another way
    // but so far the chromosome need these clases to operate:
    //Course_element[] courses, Room[] rooms, TimeSlot[] timeSlots     ---SetRooms rooms;
    
    public Chromosome(int NUMBER_OF_GENES){
        this.NUMBER_OF_GENES = NUMBER_OF_GENES;
        genes = new ArrayList<>();
        for(int i=0;i<NUMBER_OF_GENES;i++){
            genes.add(new Gene());
        }
    }
    
    public void setRandomGenes(int numberOfRooms, int numberOfTimeSlots){
        for(int i=0;i<NUMBER_OF_GENES;i++)
            genes.get(i).setAllElementsRandom(numberOfRooms,numberOfTimeSlots);
    }
    
    public void setTruncDistance(double tdis){//todo
        this.truncDistance=tdis;
    }
    
    public void setR1fromGene(int i, int roomId){
        genes.get(i).setRoom1(roomId);
    }
    public void setR2fromGene(int i, int roomId){
        genes.get(i).setRoom2(roomId);
    }
    public void setT1fromGene(int i, int roomId){
        genes.get(i).setTimeSlot1(roomId);
    }
    public void setT2fromGene(int i, int roomId){
        genes.get(i).setTimeSlot2(roomId);
    }
    
    public void setFitness(double fit){
        this.fitness = fit;
    }

    public int objective1(ArrayList<Turno> sessions){
        //HC2&HC3 "SAME LEVEL COURSES FROM A DEGREE MUST NOT OVERLAP"
        // AND "COREQUISITE COURSES FROM A DEGREE MUST NOT OVERLAP"
        //returns the number of overlap instances in the solution
        //objective is minimize this value
        int count=0;
        for (int i=0; i<NUMBER_OF_GENES; i++){
            int t1 = genes.get(i).getTimeSlotId1();
            int t2 = genes.get(i).getTimeSlotId2();
            if (sessions.get(i).getDuracion()>1 && t1 == t2) count++;            
            
            for (int j = i+1; j<NUMBER_OF_GENES;j++){
                if (sessions.get(i).colision(sessions.get(j))){
                    int jt1 = genes.get(j).getTimeSlotId1();
                    int jt2 = genes.get(j).getTimeSlotId2();

                    if (t1 == jt1) count++;
                    if (sessions.get(j).getDuracion()>1 && t1 == jt2) count++;                    
                    if (sessions.get(i).getDuracion()>1 && t2 == jt1) count++;
                    if (sessions.get(i).getDuracion()>1 &&
                        sessions.get(j).getDuracion()>1 &&
                        t2 == jt2) count++;
                }
            }
        }
        return count;
    }
    
    public int objective2(ArrayList<Turno> sessions){
        //HC4 "GIVEN 1 ROOM AND A TIMESLOT THERE CAN ONLY BE 1 COURSE"
        //returns the number or courses in a room-timeslot
        //objective is minimize this value
        int count=0;
        for (int i=0;i<NUMBER_OF_GENES;i++){
            int r1 = genes.get(i).getRoomId1();
            int r2 = genes.get(i).getRoomId2();
            int t1 = genes.get(i).getTimeSlotId1();
            int t2 = genes.get(i).getTimeSlotId2();
            if(sessions.get(i).getDuracion()>1 && r1 == r2 && t1 == t2) count++;
            
            for (int j = i+1; j<NUMBER_OF_GENES;j++){
                if(genes.get(j).getRoomId1() == r1 && genes.get(j).getTimeSlotId1() == t1) 
                    count++;
                
                if(sessions.get(j).getDuracion()>1 && 
                    genes.get(j).getRoomId2() == r1 && genes.get(j).getTimeSlotId2() == t1) 
                    count++;
                
                if(sessions.get(i).getDuracion()>1 && 
                    genes.get(j).getRoomId1() == r2 && genes.get(j).getTimeSlotId1() == t2) 
                    count++;
                
                if(sessions.get(i).getDuracion()>1 &&
                    sessions.get(j).getDuracion()>1 &&
                    genes.get(j).getRoomId2() == r2 && genes.get(j).getTimeSlotId2() == t2) 
                    count++;
            }
        }
        return count;
    }
    
    public int objective3(ArrayList<Turno> sessions, ArrayList<Aula> rooms){
        //HC4 "A ROOM CANNOT HAVE A GREATER NUMBER OF ASSISTANTS THAN ITS CAPACITY"
        int count=0;
        for (int i=0; i<NUMBER_OF_GENES; i++){
            int participants = sessions.get(i).getAlumnos();
            int r1Cap = rooms.get(genes.get(i).getRoomId1()).getMaxCapacity();
            if(r1Cap < participants) count++;
            if(sessions.get(i).getDuracion()>1){
                int r2Cap = rooms.get(genes.get(i).getRoomId2()).getMaxCapacity();
                if(r2Cap < participants) count++;
            }
        }
        return count;
    }
    
        public int objective4(ArrayList<Turno> sessions, ArrayList<Fecha> timeSlots){
        //SC1 "2 HOURS SESSIONS WILL ALWYS TRY TO HAVE CONSECUTIVE TIMESOLTS"
        int count=0;
        for (int i=0; i<NUMBER_OF_GENES; i++){
            if(sessions.get(i).getDuracion()>1){
                int d1 = timeSlots.get(genes.get(i).getTimeSlotId1()).getDia();
                int d2 = timeSlots.get(genes.get(i).getTimeSlotId2()).getDia();
                if (d1 != d2) count++;
                else{
                    int t1 = timeSlots.get(genes.get(i).getTimeSlotId1()).getHora();
                    int t2 = timeSlots.get(genes.get(i).getTimeSlotId2()).getHora();        
                    if(t1+1 != t2 && t1-1 != t2) count++;
                }
            }
        }
        return count;
    }

    public void calculateObjectives(ArrayList<Turno> sessions, ArrayList<Aula> rooms, ArrayList<Fecha> timeSlots){
        objectives = new ArrayList<>();
        objectives.add(objective1(sessions));
        objectives.add(objective2(sessions));
        objectives.add(objective3(sessions, rooms)+objective4(sessions, timeSlots));
    }
    
    public double calculateEuclideanDistance(Chromosome x2){
        double sum=0;
        for (int i =0;i<this.objectives.size();i++) 
            sum += Math.pow((this.objectives.get(i)-x2.objectives.get(i)),2.0);
        return Math.sqrt(sum);
    }
    
    public int size(){
        return this.NUMBER_OF_GENES;
    }
    
    public int getNumberOfDominatedSolutions(){
        return this.numberOfDominatedSolutions;
    }

    public Gene getGene(int i){
        return genes.get(i);
    }
    
    public double getFitness(){
        return this.fitness;
    }
          
    public ArrayList<Integer> getObjectives(){
        return this.objectives;
    }
    
    public boolean dominates(Chromosome solution){
        //since we are counting "mistakes" a value closer to 0 is considered better
        for (int i =0; i< this.objectives.size();i++){
            if (this.objectives.get(i) > solution.objectives.get(i))
                return false;
        }
        return true;
    }
    
    public void setNumberOfDominatedSolutions(int dom){
        this.numberOfDominatedSolutions = dom;
    }
    
    public double weightedSumOfObjectives(){
        double sum=0;
        for(int i=0;i<objectives.size();i++){
            sum+=objectives.get(i);
        }
        return sum;
    }
    
    public Chromosome crossover(Chromosome chromosome2, double crossoverRate){
        //spCrossover stands for 'single point crossover'
        Chromosome child = new Chromosome(NUMBER_OF_GENES);
        if(ThreadLocalRandom.current().nextDouble(1)>0.9)return this;
        for (int i=0;i<NUMBER_OF_GENES;i++){
            if(ThreadLocalRandom.current().nextDouble(1)<crossoverRate) child.genes.get(i).setRoom1(chromosome2.genes.get(i).getRoomId1());
            else child.genes.get(i).setRoom1(this.genes.get(i).getRoomId1());
            if(ThreadLocalRandom.current().nextDouble(1)<crossoverRate) child.genes.get(i).setRoom2(chromosome2.genes.get(i).getRoomId2());
            else child.genes.get(i).setRoom2(this.genes.get(i).getRoomId2());
            if(ThreadLocalRandom.current().nextDouble(1)<crossoverRate) child.genes.get(i).setTimeSlot1(chromosome2.genes.get(i).getTimeSlotId1());
            else child.genes.get(i).setTimeSlot1(this.genes.get(i).getTimeSlotId1());
            if(ThreadLocalRandom.current().nextDouble(1)<crossoverRate) child.genes.get(i).setTimeSlot2(chromosome2.genes.get(i).getTimeSlotId2());
            else child.genes.get(i).setTimeSlot2(this.genes.get(i).getTimeSlotId2());
        }
        return child;
    }
    
    public void mutate(double rate, int numberOfRooms, int numberOfTimeSlots){
        for (int i=0;i<genes.size();i++){
            if(ThreadLocalRandom.current().nextDouble(1)<rate)genes.get(i).setRoom1Random(numberOfRooms);
            if(ThreadLocalRandom.current().nextDouble(1)<rate)genes.get(i).setRoom2Random(numberOfRooms);
            if(ThreadLocalRandom.current().nextDouble(1)<rate)genes.get(i).setTimeSlot1Random(numberOfTimeSlots);
            if(ThreadLocalRandom.current().nextDouble(1)<rate)genes.get(i).setTimeSlot2Random(numberOfTimeSlots);
        }
    }
    
    @Override
    public String toString(){
        String out= "";
        int i=0;
        if(objectives != null){
            out+= " objs [";
            for(;i<objectives.size()-1;i++){
                out+=objectives.get(i)+",";
            }
            out+=objectives.get(i)+"]";
        }
        
        if (this.numberOfDominatedSolutions != -1) out+= " doms "+this.numberOfDominatedSolutions;
        if (this.fitness != -1) out+= " fit "+form.format(this.fitness)+" ";
        i=0;
        for(;i<genes.size()-1;i++){
            out+=genes.get(i).toString()+" / ";            
        }
        out+=genes.get(i).toString();

        


        return out;
    }
}

class FitnessComparator implements Comparator<Chromosome>{
    @Override
    public int compare(Chromosome x1, Chromosome x2){
        //check in the future if when making fitness private this class can access
        return Double.compare(x1.fitness, x2.fitness);
    }
}

class TruncDistanceComparator implements Comparator<Chromosome>{
    @Override
    public int compare(Chromosome x1, Chromosome x2){
        //check in the future if when making fitness private this class can access
        return Double.compare(x1.truncDistance , x2.truncDistance);
    }
}

class ObjectivesSumComparator implements Comparator<Chromosome>{
    @Override
    public int compare(Chromosome x1, Chromosome x2){
        //check in the future if when making fitness private this class can access
        //return Double.compare(x1.objectives , x2.truncDistance);
        double sum1 = x1.weightedSumOfObjectives();
        double sum2 = x2.weightedSumOfObjectives();
        return Double.compare(sum1, sum2);
    }
}


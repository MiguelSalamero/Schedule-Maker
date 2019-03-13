package domain;
import java.util.ArrayList;
import java.util.Collections;
import domain.Turno;
import java.util.concurrent.ThreadLocalRandom;
public class Population {
    private ArrayList<Chromosome> population;    
    private int populationSize;
    
    public Population(){
        this.population = new ArrayList<>();
        this.populationSize=0;
    }
    public Population(int numberOfSesisons, int numberOfRooms, int numberOfTimeSlots, int populationSize){
        this.populationSize = populationSize;
        this.population = new ArrayList<>(populationSize);
        for (int i=0;i<populationSize;i++){
            Chromosome solution = new Chromosome(numberOfSesisons);
            solution.setRandomGenes(numberOfRooms,numberOfTimeSlots);
            this.population.add(solution);
        }
    }
        
    public Population(Population p1, Population p2){
        this.population = new ArrayList<>();
        this.population.addAll(p1.population); 
        //there may be duplicates so its not exactly a union
        //depending on how it performances ill change it later
        this.population.addAll(p2.population); 
        this.populationSize = this.population.size();
    }
    
    public void addChromosome(Chromosome solution){
        this.population.add(solution);
        this.populationSize++;
    }
    
    public void sortByFitness(){
        Collections.sort(this.population, new FitnessComparator());
    }

    public void sortByTruncDistance(){
        Collections.sort(this.population, new TruncDistanceComparator());
    }
    public void sortByObjectivesSum(){
        Collections.sort(this.population, new ObjectivesSumComparator());
    }
    
    public Chromosome getSolution(int i){
        return population.get(i);
    }
    
    public int size(){
        return populationSize;
    }

    public void calculatePopulationObjectives(ArrayList<Turno> sessions, ArrayList<Aula> rooms, ArrayList<Fecha> timeSlots){
        for (int i=0;i<populationSize;i++){
            population.get(i).calculateObjectives(sessions, rooms, timeSlots);
        }
    }
    
    public void calculateDominations(){       
        for (int i=0;i<populationSize;i++){
            int numberOfDominatedSolutions =0;
            for (int j=0;j<populationSize;j++){
                if( i!= j && population.get(i).dominates(population.get(j)))
                    numberOfDominatedSolutions++;               
            }
            population.get(i).setNumberOfDominatedSolutions(numberOfDominatedSolutions);
        }
    }

    public double calculateRawFitnessOfSolution(int i){
        //check this in the future
        double sum=0;
        for(int j=0;j<this.populationSize;j++){
            if(population.get(j).dominates(population.get(i))) 
                sum += (double)population.get(j).getNumberOfDominatedSolutions();
        }
        return sum;
    }
    
    public double calculateDensityOfSolution(int i){
        ArrayList<Double> distances = new ArrayList<>();
        for(int j=0;j<populationSize;j++){
            distances.add(population.get(j).calculateEuclideanDistance(population.get(i)));
        }  
        Collections.sort(distances);
        int k = 1; //set to 1 for efficiency otherwise (int)Math.sqrt(populationSize);
        return 1.0/(distances.get(k)+2.0);
    }
    
    public void calculateFitness(){
        this.calculateDominations();
        for(int i=0;i<populationSize;i++){
            double rawFitness = this.calculateRawFitnessOfSolution(i);
            double density = this.calculateDensityOfSolution(i);
            population.get(i).setFitness(rawFitness+density);
        }
    }
    
    public void truncate(int tosize){
        int k = 1; //efficiency (int) Math.sqrt(populationSize);
        ArrayList<Double> distances;
        for (int i=0; i<populationSize;i++){
            distances = new ArrayList<>();
            for (int j=0; j<populationSize;j++){
                distances.add(population.get(i).calculateEuclideanDistance(population.get(j)));
            }
            Collections.sort(distances);
            population.get(i).setTruncDistance(distances.get(k));
        }
        this.sortByTruncDistance();//does this remove elements from another pop that contains it?
        this.population = new ArrayList<>(this.population.subList(0, tosize));
        this.populationSize = tosize;
    }  
    
    public Chromosome binaryTournament(){
        int i = ThreadLocalRandom.current().nextInt(0, populationSize);
        int j = ThreadLocalRandom.current().nextInt(0, populationSize);
        if (population.get(i).getFitness() < population.get(j).getFitness())
            return population.get(i);
        return population.get(j);
    }
    
    public Population EnvironmentalSelection(int archiveSize){
        Population selected = new Population();
        this.sortByFitness();
        int i=0;
        while(i<populationSize && population.get(i).getFitness() < 1.0){
            //first step: add all solutions with fitness<1
            selected.addChromosome(population.get(i));  
            i++;
        }
        while(i<populationSize && i<archiveSize){
            selected.addChromosome(population.get(i));  
            i++;
        }
        if (selected.size() > archiveSize){
            selected.truncate(archiveSize);            
        }
        return selected;
    }
    
    public Population reproduce(double crossOverRate, double mutationRate, int numberOfRooms, int numberOfTimeSlots){
        Population new_pop = new Population();
        for(int i=0;i<populationSize;i++){
            Chromosome solution1 = population.get(i);
            Chromosome solution2;
            if (i == populationSize-1) solution2 = population.get(0);
            else if(i%2 == 0) solution2 = population.get(i+1);
            else solution2 = population.get(i-1);
            Chromosome child = solution1.crossover(solution2, crossOverRate);
            child.mutate(mutationRate,numberOfRooms, numberOfTimeSlots);
            new_pop.addChromosome(child);

        }
        return new_pop;
    }
    
    @Override
    public String toString(){
        String out = "size "+this.populationSize+"\n";
        for(int i=0; i<this.populationSize;i++){
            out+=population.get(i).toString()+"\n";
        }
        return out;
    }
}

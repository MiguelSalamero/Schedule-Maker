package domain;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Locale;

public class GeneticAlgorithm {
    private  Integer MAX_GENERATION = 40000;
    private   Integer POPULATION_SIZE = 40;
    private   Integer ARCHIVE_SIZE = 30;
    private   Double CROSSOVER_RATE = 0.5;
    private  Double MUTATION_RATE = 0.01;
    
    private final int NUMBER_OF_SESSIONS;
    private final int NUMBER_OF_ROOMS;
    private final int NUMBER_OF_TIMESLOTS;
    
    private final ArrayList<Turno> sessions;
    private final ArrayList<Aula> rooms;
    private final ArrayList<Fecha> timeSlots;
    
    public GeneticAlgorithm(ArrayList<Turno> sessions, ArrayList<Aula> rooms, ArrayList<Fecha> timeSlots){
        Locale.setDefault(Locale.US);
        this.NUMBER_OF_SESSIONS = sessions.size();
        this.NUMBER_OF_ROOMS = rooms.size();
        this.NUMBER_OF_TIMESLOTS = timeSlots.size();
        this.MUTATION_RATE = 1.0/((double)NUMBER_OF_SESSIONS*4.0);
        
        this.sessions = sessions;
        this.rooms=rooms;
        this.timeSlots = timeSlots;
    }
    
    public void loadParameters()throws FileNotFoundException, IOException {
        BufferedReader in = new BufferedReader(new FileReader("parametros.txt"));
        String linea;
        int i=0;
        for(;i<9;i++)
        {//skip the information lines
            in.readLine();
        }    
        MAX_GENERATION = Integer.parseInt(in.readLine());
        in.readLine(); in.readLine();
        POPULATION_SIZE = Integer.parseInt(in.readLine());
        in.readLine(); in.readLine();
        ARCHIVE_SIZE = Integer.parseInt(in.readLine());
        in.readLine(); in.readLine();
        CROSSOVER_RATE = Double.parseDouble(in.readLine());
        in.readLine(); in.readLine();
        double mr = Double.parseDouble(in.readLine());
        if(mr >= 0) MUTATION_RATE = mr;

    }
    
    public String showPArameters(){
        String out = String.format("MAX_GENERATION=%d, POPULATION_SIZE=%d, ARCHIVE_SIZE=%d, CROSSOVER_RATE=%.2f, MUTATION_RATE=%.2f",MAX_GENERATION,POPULATION_SIZE,ARCHIVE_SIZE,CROSSOVER_RATE,MUTATION_RATE);
        return out;
    }
    
    public void setParametrosAlgoritmo(String MAX_G, String POP_SIZE, String AR_SIZE, String CROSS_RATE, String MUT_RATE) {
        MAX_GENERATION= Integer.parseInt(MAX_G);
        POPULATION_SIZE= Integer.parseInt(POP_SIZE);
        ARCHIVE_SIZE= Integer.parseInt(AR_SIZE);
        CROSSOVER_RATE= Double.parseDouble(CROSS_RATE);
        MUTATION_RATE= Double.parseDouble(MUT_RATE);      
    }
    public String[] getParametrosAlgoritmo() {
                
        String[] parametros = new String[5];
        parametros[0] = MAX_GENERATION.toString();
        parametros[1] = POPULATION_SIZE.toString();
        parametros[2] = ARCHIVE_SIZE.toString();
        parametros[3] = String.format("%.4f", CROSSOVER_RATE);
        parametros[4] = String.format("%.4f", MUTATION_RATE);
        return parametros;
    }
    
    public ArrayList<SesionAsignatura> Execute(){
        ArrayList<SesionAsignatura> schedule = new ArrayList<>();
        //1) create an initial population of random solutions
        Population population = new Population(NUMBER_OF_SESSIONS, NUMBER_OF_ROOMS, NUMBER_OF_TIMESLOTS, POPULATION_SIZE);
        //^this constructor is abosulute trash -> replace
        //2) initialize an archive as an empty set
        Population archive = new Population();

        Chromosome bestSolution;
        Population mantingPool;
        int generation = 0;
        //until reaching maximum generation or another end condition:
        while (generation < MAX_GENERATION){
            //3)calculate fitness on the union of (population+archive)
            population.calculatePopulationObjectives(sessions, rooms, timeSlots);
            Population union = new Population(population,archive);
            union.calculateFitness();

            //4) create archive from the fittests solutions
            
            archive = union.EnvironmentalSelection(ARCHIVE_SIZE);
            //archive = Selection.EnvironmentalSelection(population, archive, ARCHIVE_SIZE);

            archive.sortByObjectivesSum();

            bestSolution = archive.getSolution(0);
            if(generation%500 == 0){
                //System.out.println("best solution of gen "+generation);
                //System.out.println(bestSolution.toString());
            }
            if(bestSolution.weightedSumOfObjectives() == 0){
                //System.out.println("best solution of gen " + generation);
                //System.out.println(bestSolution.toString());
                for(int i=0;i<bestSolution.size();i++) {
                    schedule.add(new SesionAsignatura(sessions.get(i), timeSlots.get(bestSolution.getGene(i).getTimeSlotId1()), rooms.get(bestSolution.getGene(i).getRoomId1()), false));
                    if(sessions.get(i).getDuracion() > 1)
                        schedule.add(new SesionAsignatura(sessions.get(i), timeSlots.get(bestSolution.getGene(i).getTimeSlotId2()), rooms.get(bestSolution.getGene(i).getRoomId2()), false));
                }
                break;
            }
            mantingPool = new Population();
            for(int i=0;i<POPULATION_SIZE;i++)
                mantingPool.addChromosome(archive.binaryTournament());
//            System.out.println("pop");
//            System.out.println(population.toString());
//            System.out.println("archive");
//            System.out.println(archive.toString());
//            System.out.println("manting");
//            System.out.println(mantingPool.toString());
            population = mantingPool.reproduce(CROSSOVER_RATE, MUTATION_RATE, NUMBER_OF_ROOMS, NUMBER_OF_TIMESLOTS);
            generation++;

        }
        return schedule;
    }
}

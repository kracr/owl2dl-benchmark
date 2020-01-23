/*generates axioms that generate cross links between departments of same college, colleges of same university, different universities.*/
/* The default values for random range (min and max for each parameter) are specified in the generator.java file. 
 In order to modify the min-max range,that is, to modify the density of each node, user can make changes in the ConfigFile.java file */

package ABoxGen.InstanceGenerator;

import java.util.HashSet;
import java.util.Iterator;

public class InterlinkedProperties {


    University university;
    GetRandomPerson getRandomPerson;
    GetRandomInterest getRandomInterest;
    University universities[];
    ConfigFile configFile;
    Generator gen;
    String crazyAbout,profile;
    HashSet<String> hash1,hash2,hash3,hash4,hash5;
    int sameHomeTownNum,isFriendOfNum,likesNum,lovesNum,isCrazyAboutNum,dislikesNum;


    public InterlinkedProperties(Generator gen,University universities[]){
        this.gen=gen;
        this.profile=gen.profile;
        //this.univNum=gen.univNum;
        this.universities=universities;
        getRandomInterest=new GetRandomInterest();
        getRandomPerson=new GetRandomPerson();
        configFile=new ConfigFile();
        for(int i=0;i<gen.univNum;++i){
            university=universities[i];
            Iterator<String> j=university.personPerUniversity.iterator();

     
            while(j.hasNext()) {
                String person1=j.next();
                sameHomeTownNum = GetRandomNo.getRandomFromRange(gen.sameHomeTownNum_Min,gen.sameHomeTownNum_Max); //between 0 and 3
                // isFriendOfNum = GetRandomNo.getRandomFromRange(gen.isFriendOfNum_Min,gen.isFriendOfNum_Max);
                likesNum=GetRandomNo.getRandomFromRange(gen.likesNum_Min,gen.likesNum_Max); // between 1 and 3
                lovesNum=GetRandomNo.getRandomFromRange(gen.lovesNum_Min,gen.lovesNum_Max); //between 0 and 2
                isCrazyAboutNum=GetRandomNo.getRandomFromRange(gen.isCrazyAboutNum_Min,gen.isCrazyAboutNum_Max); // between 0 and 1
                dislikesNum=GetRandomNo.getRandomFromRange(gen.dislikesNum_Min,gen.dislikesNum_Max); // between 0 and 1

                hash1=new HashSet();
                for (int a = 0; a < sameHomeTownNum; ++a) {
                    String person = getRandomPerson.getRandomStudentFacultyOrStaff(gen,universities);
                    if (person != null) {
                        hash1.add(person);
                    }
                    Iterator<String> k = hash1.iterator();
                    //:jon :hasSameHomeTownWith :Mac
                    while (k.hasNext()) {
                        gen.objectPropertyAssertion(gen.getObjectProperty("hasSameHomeTownWith"),gen.getNamedIndividual(person1),gen.getNamedIndividual(k.next()));
                    }
                }

                hash3= new HashSet();
                for (int a = 0; a < likesNum; ++a) {
                    String interest = getRandomInterest.getInterest();
                    if (interest != null) {
                        hash3.add(interest);
                    }
                    Iterator<String> k = hash3.iterator();
                    while (k.hasNext()) {
                    	//:jon :likes :cricket
                        gen.objectPropertyAssertion(gen.getObjectProperty("likes"),gen.getNamedIndividual(person1),gen.getNamedIndividual(k.next()));
                    }
                }
                hash4= new HashSet();
                //QL TBox doesn't have object property loves
                if ((profile.matches("DL")) || (profile.matches("RL"))|| (profile.matches("EL"))) {
                for (int a = 0; a < lovesNum; ++a) {
                    String interest = getRandomInterest.getInterest();
                    if ((interest != null) && (!hash3.contains(interest))) {
                        hash4.add(interest);
                    }
                    Iterator<String> k = hash4.iterator();
                    while (k.hasNext()) {
                    	//:jon :loves :football
                        gen.objectPropertyAssertion(gen.getObjectProperty("loves"),gen.getNamedIndividual(person1),gen.getNamedIndividual(k.next()));
                    }
                }
                }
                //isCrazyAbout just one
                if(isCrazyAboutNum!=0) {
                    crazyAbout=getRandomInterest.getInterest();
                    //:jon :isCrazyAbout :tennis
                    gen.objectPropertyAssertion(gen.getObjectProperty("isCrazyAbout"),gen.getNamedIndividual(person1),gen.getNamedIndividual(crazyAbout));
                }
                //only DL, Ql, Rl support disjoint object properties. So, dislikes doesnt belong to EL TBox
                if ((profile.matches("DL")) || (profile.matches("RL"))|| (profile.matches("QL"))) {
                hash5= new HashSet();
                for (int a = 0; a < dislikesNum; ++a) {
                    String interest = getRandomInterest.getInterest();
                    if ((interest != null) && (crazyAbout!=interest) && (!hash3.contains(interest)) && (!hash4.contains(interest))) {
                        hash5.add(interest);
                    }
                    Iterator<String> k = hash5.iterator();
                    while (k.hasNext()) {
                    	//:jon :dislikes :music
                        gen.objectPropertyAssertion(gen.getObjectProperty("dislikes"),gen.getNamedIndividual(person1),gen.getNamedIndividual(k.next()));
                    }
                }
                }
            }
        }
    }
}

/*
hash2= new HashSet();
for (int a = 0; a < isFriendOfNum; ++a) {
    String person = getRandomPerson.getRandomStudentFacultyOrStaff(gen,universities);
    if (person != null) {
        hash2.add(person);
    }
    Iterator<String> k = hash2.iterator();
    while (k.hasNext()) {
        gen.objectPropertyAssertion(gen.getObjectProperty("isFriendOf"),gen.getNamedIndividual(person1),gen.getNamedIndividual(k.next()));
    }
}
*/

package com.company;

import java.util.Random;

public class Main {

    public static int bossHealth = 600;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {260, 250, 270,250,200,200,200,400};
    public static int[] heroesDamage = {20, 20, 10,0,10,15,0,5};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic","Medik","Berserk",
            "Thor","Lucky","Golem"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();

        while (!isGameFinished()) {
            round();
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss chose: " + bossDefenceType);
    }

    public static void round() {
        roundNumber++;
        chooseBossDefence();
        bossHits();
        heroesHit();
        thor();
        printStatistics();
        medikHeal();
        luckyEvasion();
        berserk();
        golem();

    }

    private static void thor() {
        Random random= new Random();
        boolean stun= random.nextBoolean();
        if (stun){
            bossDamage=0;
            System.out.println("Босс оглушен");
        }else{
            bossDamage = 50;
        }
    }

    private static void berserk() {
       Random random=new Random();
       int randomDamage=random.nextInt(5)+1;
       int randomRound=random.nextInt(5);
       if (heroesHealth[4]>0&&bossHealth>0){
           switch (randomRound){
               case 1:
                   heroesDamage[4]=heroesDamage[4]+bossDamage - randomDamage;
                   System.out.println("Берсерк урон критический");
                   System.out.println("Потери при увеличении урона "+ randomDamage);
                   break;
               case 2:
               case 3:
               case 4:

           }
       }
    }

    private static void luckyEvasion() {
        Random random= new Random();
        int randomEvasion=random.nextInt(4)+1;
        switch (randomEvasion){
            case 1:
                heroesHealth[7]=heroesHealth[6]+ bossDamage;
                System.out.println("Уклонился");
                break;
            case 2:

            case 3:

        }
    }

    private static void golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[7] > 0 && heroesHealth[i] > 0 && heroesHealth[7]!= heroesHealth[i]) {
               heroesHealth[i] += bossDamage/5;
               heroesHealth[7] -= bossDamage/5;
            }
        }
    }

    public static void medikHeal() {
        for (int i = 0; i < 1 ; i++) {
            Random random = new Random();
            int randomHeal=random.nextInt(40);
            int randomHero=random.nextInt(2);
            if (heroesHealth[i] > 0 && heroesHealth[7] > 0 && heroesHealth[i]<100 ) {
                heroesHealth[randomHero]=heroesHealth[randomHero] + randomHeal;
                System.out.println("Медик вылечил " + randomHeal );
            }
        }
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (bossHealth > 0 && heroesHealth[i] > 0) {
                if (bossDefenceType == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println("Critical damage: " + heroesDamage[i] * coeff);
                } else {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i]; //  bossHealth -= heroesDamage[i];
                    }
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println(roundNumber + " ROUND -------------");
        System.out.println("Boss health: " + bossHealth + " (" + bossDamage + ")");
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: "
                    + heroesHealth[i] + " (" + heroesDamage[i] + ")");
        }
    }
}

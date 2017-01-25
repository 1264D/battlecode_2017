package Committee;
import battlecode.common.*;

import java.lang.reflect.Array;
import java.util.*;

import static battlecode.common.RobotInfo.*;
import static java.lang.Math.sqrt;

public strictfp class RobotPlayer {
    static RobotController rc;
    static Random myRand;
    static int ARCHON_ID = 1;
    static int GLOCATION = 2;
    static int DEAD_SCOUT_SWITCH = 35;
    static int GARD_NUM = 0;

    /**
     * run() is the method that is called when a robot is instantiated in the Battlecode world.
     * If this method returns, the robot dies!
    **/
    @SuppressWarnings("unused")
    public static void run(RobotController rc) throws GameActionException {

        // This is the RobotController object. You use it to perform actions from this robot,
        // and to get information on its current status.
        RobotPlayer.rc = rc;
        myRand = new Random(rc.getID());


        // Here, we've separated the controls into a different method for each RobotType.
        // You can add the missing ones or rewrite this into your own control structure.
        switch (rc.getType()) {
            case ARCHON:
                runArchon();
                break;
            case GARDENER:
                runGardener();
                break;
            case SOLDIER:
                runSoldier();
                break;
            case LUMBERJACK:
                runLumberjack();
                break;
            case SCOUT:
                runScout();
                break;
        }
	}

    static void runArchon() throws GameActionException {

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                int scoutNum;
                int numOfArc = rc.getInitialArchonLocations(rc.getTeam()).length;
                if (rc.readBroadcast(DEAD_SCOUT_SWITCH) < (rc.getRoundNum() - 2)){
                    scoutNum = 0;
                } else {
                    scoutNum = 1;
                }

                GARD_NUM = rc.getRobotCount() - (numOfArc + scoutNum);
                System.out.println(GARD_NUM);
                float currentBulletCost = (float)(7.5 + (rc.getRoundNum()*(12.5/3000)));
                System.out.println("Robot count= " + rc.getRobotCount());
                System.out.println("Scout dead= " + scoutNum);

                Direction hireDir = randomDirection();
                int stuck = 0;
                System.out.println("Prewhiel bytes:" + Clock.getBytecodesLeft());
                while(!rc.canHireGardener(hireDir) && stuck < 51){
                    hireDir = randomDirection();
                    stuck += 1;
                }
                if (GARD_NUM < 9 && rc.canHireGardener(hireDir) && rc.senseNearbyRobots(12).length <= 2){
                    rc.hireGardener(hireDir);
                }
                System.out.println("Posthire bytes:" + Clock.getBytecodesLeft());
                if(rc.getTeamBullets() >= 125 && rc.getTreeCount() >= 14 && GARD_NUM >= 9){
                    while (rc.getTeamBullets() >= 75) {
                        rc.donate(currentBulletCost);
                    }
                }
                if(rc.getTeamBullets() >= 150 && rc.getTreeCount() >= 14 && GARD_NUM < 9){
                    while (rc.getTeamBullets() >= 125) {
                        rc.donate(currentBulletCost);
                    }
                }
                if (rc.getTeamBullets() >= 190 && rc.getRoundNum() >= 8) {
                    while (rc.getTeamBullets() >= 160){
                        rc.donate(currentBulletCost);
                    }
                }
                if(rc.getTeamBullets() >= 75 && rc.getTreeCount() >= 25){
                    while (rc.getTeamBullets() >= 50) {
                        rc.donate(currentBulletCost);
                    }
                }
                if (rc.getRoundNum() == (rc.getRoundLimit()-1)){
                    rc.donate(rc.getTeamBullets());
                }


                Clock.yield();

            } catch (Exception e) {
                System.out.println("Archon Exception");
                e.printStackTrace();
            }
        }
    }

	static void runGardener() throws GameActionException {

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                if(rc.getRoundNum() == 2) {
                    Direction spawnDir = randomDirection();
                    int stuck = 0;

                    while (!rc.canBuildRobot(RobotType.SCOUT, spawnDir) && stuck < 51) {
                        spawnDir = randomDirection();
                        stuck += 1;
                    }
                    if (GARD_NUM < 4 && rc.canBuildRobot(RobotType.SCOUT, spawnDir)) {
                        rc.buildRobot(RobotType.SCOUT, spawnDir);
                    }
                }
                if (rc.isCircleOccupiedExceptByThisRobot(rc.getLocation(),5) && rc.readBroadcast(IDScrub(rc.getID())) != 10){
                    wander();
                    rc.setIndicatorDot(rc.getLocation(), 200, 6, 10);

                }

                //System.out.println("Status around: " + rc.isCircleOccupiedExceptByThisRobot(rc.getLocation(),5));
                if (!rc.isCircleOccupiedExceptByThisRobot(rc.getLocation(),5) || rc.readBroadcast(IDScrub(rc.getID())) == 10){
                    rc.setIndicatorDot(rc.getLocation(), 0, 186, 40);
                    rc.broadcast(IDScrub(rc.getID()),10);
                    plantSequence(rc.getLocation());
                    TreeInfo[] trees = rc.senseNearbyTrees(2);
                    for(TreeInfo t : trees){
                        //System.out.println(t.getID());
                        if(rc.canWater(t.getID())) {
                            if (t.getHealth() <= (t.getMaxHealth()-4.5)) {
                                rc.water(t.getID());
                                //System.out.println(t.getHealth());
                                //System.out.println(t.getMaxHealth());
                            }
                        }
                    }
                }







                Clock.yield();

            } catch (Exception e) {
                System.out.println("Gardener Exception");
                e.printStackTrace();
            }
        }
    }

    static void runScout() throws GameActionException {

        while(1==1){
            try{


                List nearTrees = new ArrayList();
                TreeInfo[] trerres = rc.senseNearbyTrees();
                for (TreeInfo p : trerres){
                    if (p.getContainedBullets() > 0) {
                        nearTrees.add(p.getID());
                    }
                }

                rc.broadcast(DEAD_SCOUT_SWITCH,rc.getRoundNum());

                if(nearTrees.size() == 0){
                    wander();
                    rc.setIndicatorDot(rc.getLocation(),180,0,0);
                }
                if (nearTrees.size() != 0 ){
                    if(rc.getLocation().distanceTo(rc.senseTree((int)nearTrees.get(0)).getLocation()) > 2.5) {
                        rc.setIndicatorDot(rc.getLocation(), 0, 180, 0);
                        System.out.println(rc.getLocation().distanceTo(rc.senseTree((int)nearTrees.get(0)).getLocation()));
                        tryMove(new Direction(rc.getLocation(),rc.senseTree((int)nearTrees.get(0)).getLocation()));
                    }
                    if(rc.getLocation().distanceTo(rc.senseTree((int)nearTrees.get(0)).getLocation()) < 2.5) {
                        rc.setIndicatorDot(rc.getLocation(), 0, 50, 150);
                        System.out.println(rc.getLocation().distanceTo(rc.senseTree((int)nearTrees.get(0)).getLocation()));
                        rc.shake((int) nearTrees.get(0));
                    }



                }

/*
                TreeInfo[] trees = rc.senseNearbyTrees();
                for(TreeInfo t : trees){
                    if(rc.canShake(t.getLocation())){
                        rc.shake(t.getLocation());
                    }
                }
                wander(); */

                Clock.yield();
            }
            catch(Exception e){
                System.out.println("Scout Exception");
                e.printStackTrace();
            }
        }
    }
    static void runSoldier() throws GameActionException {

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                dodge();

                Clock.yield();

            } catch (Exception e) {
                System.out.println("Soldier Exception");
                e.printStackTrace();
            }
        }
    }

    static void runLumberjack() throws GameActionException {
        System.out.println("I'm a lumberjack!");
        Team enemy = rc.getTeam().opponent();

        // The code you want your robot to perform every round should be in this loop
        while (true) {

            // Try/catch blocks stop unhandled exceptions, which cause your robot to explode
            try {
                dodge();

                Clock.yield();

            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
        }
    }

    public static void wander() throws GameActionException {
        MapLocation[] archons = rc.getInitialArchonLocations(rc.getTeam());
        //System.out.println(archons);
        //System.out.println(archons[1].y);
        float nearestDistance = 1000;
        MapLocation nearestArc = rc.getLocation();
        for(MapLocation a : archons){
            if (a.distanceTo(rc.getLocation()) < nearestDistance){
                nearestDistance = a.distanceTo(rc.getLocation());
                nearestArc = a;
            }
        }
        if(rc.canSenseLocation(nearestArc) && nearestArc != rc.getLocation()) {
            Direction dir = rc.getLocation().directionTo(nearestArc);
            //if (rc.getType() == RobotType.SCOUT) {
                while (dir.getAngleDegrees() > rc.getLocation().directionTo(nearestArc).getAngleDegrees() - 45 && dir.getAngleDegrees() < rc.getLocation().directionTo(nearestArc).getAngleDegrees() + 45) {
                    dir = randomDirection();
                }
                tryMove(dir);
            //} else {
             //   tryMove(dir.opposite());
            //}
        }
        else{
            tryMove(randomDirection());
        }
    }

    public static int IDScrub(int ID) throws GameActionException {
        return ID % 10001;
    }

    public static boolean goHome(MapLocation homeLoc) throws GameActionException {
        if (rc.getLocation().x != homeLoc.x && rc.getLocation().y != homeLoc.y) {
            if (rc.canMove(homeLoc)) { rc.move(homeLoc); }
            return false;
        }
        else {
            return true;
        }
    }

    public static void tryPlant(Direction where) throws GameActionException {
        if(rc.canPlantTree(where)) {
            rc.plantTree(where);
        }
    }

    public static void plantSequence(MapLocation robLoc) throws GameActionException {
        float spawnX = (float)1.005;
        float spawnY = (float)1.74;

        if(rc.senseTreeAtLocation(robLoc.translate(-spawnX,spawnY)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(2.0/3))))) {
            tryPlant(new Direction((float)((Math.PI)*(2.0/3))));
        } else { if(rc.senseTreeAtLocation(robLoc.translate(spawnX,-spawnY)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(5.0/3))))) {
                tryPlant(new Direction((float)((Math.PI)*(5.0/3))));
            } else { if(rc.senseTreeAtLocation(robLoc.translate(spawnX,spawnY)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(1.0/3))))) {
                    tryPlant(new Direction((float)((Math.PI)*(1.0/3))));

                } else { if(rc.senseTreeAtLocation(robLoc.translate(-spawnX,-spawnY)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(4.0/3))))){
                        tryPlant(new Direction((float)((Math.PI)*(4.0/3))));
                    } else { if(rc.senseTreeAtLocation(robLoc.translate((float)1.01,0)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(6.0/3))))){
                            tryPlant(new Direction((float)((Math.PI)*(6.0/3))));
                        } else { if(rc.senseTreeAtLocation(robLoc.translate((float)-1.01,0)) == null && rc.canPlantTree(new Direction((float)((Math.PI)*(3.0/3))))){
                                tryPlant(new Direction((float)((Math.PI)*(3.0/3))));
                            } else {
                                System.out.println("I'm done");
                            }
                        }
                    }
                }
            }
        }
    }

    public static void tryHire(Direction direc,int locID) throws GameActionException {
        float spawnDist = (float)Math.sqrt(9.06949);

        if (rc.canHireGardener(direc)) {
            rc.hireGardener(direc);
            rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(direc, spawnDist)).getID()), locID);
        }
    }


    public static Direction randomDirection() {
        return(new Direction(myRand.nextFloat()*2*(float)Math.PI));
    }

    static boolean willCollideWithMe(BulletInfo bullet) {
        MapLocation myLocation = rc.getLocation();

        // Get relevant bullet information
        Direction propagationDirection = bullet.dir;
        MapLocation bulletLocation = bullet.location;

        // Calculate bullet relations to this robot
        Direction directionToRobot = bulletLocation.directionTo(myLocation);
        float distToRobot = bulletLocation.distanceTo(myLocation);
        float theta = propagationDirection.radiansBetween(directionToRobot);

        // If theta > 90 degrees, then the bullet is traveling away from us and we can break early
        if (Math.abs(theta) > Math.PI / 2) {
            return false;
        }

        // distToRobot is our hypotenuse, theta is our angle, and we want to know this length of the opposite leg.
        // This is the distance of a line that goes from myLocation and intersects perpendicularly with propagationDirection.
        // This corresponds to the smallest radius circle centered at our location that would intersect with the
        // line that is the path of the bullet.
        float perpendicularDist = (float) Math.abs(distToRobot * Math.sin(theta)); // soh cah toa :)

        return (perpendicularDist <= rc.getType().bodyRadius);
    }

    /**
     * Attempts to move in a given direction, while avoiding small obstacles directly in the path.
     *
     * @param dir The intended direction of movement
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir) throws GameActionException {
        return tryMove(dir,20,3);
    }


    /**
     * Attempts to move in a given direction, while avoiding small obstacles direction in the path.
     *
     * @param dir The intended direction of movement
     * @param degreeOffset Spacing between checked directions (degrees)
     * @param checksPerSide Number of extra directions checked on each side, if intended direction was unavailable
     * @return true if a move was performed
     * @throws GameActionException
     */
    static boolean tryMove(Direction dir, float degreeOffset, int checksPerSide) throws GameActionException {

        // First, try intended direction
        if (!rc.hasMoved() && rc.canMove(dir)) {
            rc.move(dir);
            return true;
        }

        // Now try a bunch of similar angles
        //boolean moved = rc.hasMoved();
        int currentCheck = 1;

        while(currentCheck<=checksPerSide) {
            // Try the offset of the left side
            if(!rc.hasMoved() && rc.canMove(dir.rotateLeftDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateLeftDegrees(degreeOffset*currentCheck));
                return true;
            }
            // Try the offset on the right side
            if(! rc.hasMoved() && rc.canMove(dir.rotateRightDegrees(degreeOffset*currentCheck))) {
                rc.move(dir.rotateRightDegrees(degreeOffset*currentCheck));
                return true;
            }
            // No move performed, try slightly further
            currentCheck++;
        }

        // A move never happened, so return false.
        return false;
    }

    static boolean trySidestep(BulletInfo bullet) throws GameActionException{

        Direction towards = bullet.getDir();
        MapLocation leftGoal = rc.getLocation().add(towards.rotateLeftDegrees(90), rc.getType().bodyRadius);
        MapLocation rightGoal = rc.getLocation().add(towards.rotateRightDegrees(90), rc.getType().bodyRadius);

        return(tryMove(towards.rotateRightDegrees(90)) || tryMove(towards.rotateLeftDegrees(90)));
    }

    static void dodge() throws GameActionException {
        BulletInfo[] bullets = rc.senseNearbyBullets();
        RobotInfo[] robots = rc.senseNearbyRobots();
        for (BulletInfo bi : bullets) {
            if (willCollideWithMe(bi)) {
                trySidestep(bi);
            }
        }
        for(RobotInfo r : robots){
            if(r.getTeam() != rc.getTeam()){
                tryMove( new Direction(r.getLocation(),rc.getLocation()));
            }
        }
    }

}

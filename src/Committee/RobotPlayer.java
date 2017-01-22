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
                if(rc.getRoundNum() == 1 && rc.readBroadcast(ARCHON_ID) <= rc.getID()){
                    rc.broadcast(ARCHON_ID, rc.getID());
                }
                if(rc.getID() == rc.readBroadcast(ARCHON_ID) && rc.getRoundNum() != 1) {
                    if (rc.getRoundNum() == 2) {
                        rc.hireGardener(Direction.EAST);

                    }
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
                if(rc.getRoundNum() == 3){
                    rc.buildRobot(RobotType.SCOUT, Direction.NORTH);
                }
                if (rc.isCircleOccupiedExceptByThisRobot(rc.getLocation(),5) && rc.readBroadcast(IDScrub(rc.getID())) != 10){
                    wander();
                    rc.setIndicatorDot(rc.getLocation(), 200, 6, 10);

                }
                if (rc.isCircleOccupiedExceptByThisRobot(rc.getLocation(),5) != true || rc.readBroadcast(IDScrub(rc.getID())) == 10){
                    rc.setIndicatorDot(rc.getLocation(), 0, 186, 90);
                    rc.broadcast(IDScrub(rc.getID()),10);
                    plantSequence(rc.getLocation());
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
        Direction dir = rc.getLocation().directionTo(rc.senseRobot(rc.readBroadcast(ARCHON_ID)).location);
        while(dir.getAngleDegrees() < rc.getLocation().directionTo(rc.senseRobot(rc.readBroadcast(ARCHON_ID)).location).getAngleDegrees() - 45 || dir.getAngleDegrees() > rc.getLocation().directionTo(rc.senseRobot(rc.readBroadcast(ARCHON_ID)).location).getAngleDegrees() + 45){
             dir = randomDirection();
        }
        tryMove(dir);
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
        if(rc.isLocationOccupiedByTree(new MapLocation(-2,3))) {
            tryPlant(new Direction(-2,3));
        } else { if(rc.isLocationOccupiedByTree(new MapLocation(2,-3))) {

            } else { if(rc.isLocationOccupiedByTree(new MapLocation(1,3))) {

                } else { if(rc.isLocationOccupiedByTree(new MapLocation(-1,-3))){

                    } else { if(rc.isLocationOccupiedByTree(new MapLocation(3,0))){

                        } else { if(rc.isLocationOccupiedByTree(new MapLocation(-3,0))){

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

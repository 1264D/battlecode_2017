package Voting;
import battlecode.common.*;
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
                Direction leftUp = new Direction(-5, 2); //loc 1
                Direction upRight = new Direction(2, 5); //loc 2
                Direction rightDown = new Direction(5, -2); //loc 3
                Direction downLeft = new Direction(-2, -5); //loc 4
                Direction leftDown = new Direction(-5, -2); //loc 5
                Direction upLeft = new Direction(-2, 5); //loc 6
                Direction rightUp = new Direction(5, 2); //loc 7
                Direction downRight = new Direction(2, -5); //loc 8
                float fiveTwo = (float)Math.sqrt(29.0);
                //float fourOne = (float)Math.sqrt(17.0);
                //float spawnDist = (float)Math.sqrt(9.06949);

                //if(rc.getRoundNum() == 1 && rc.readBroadcast(ARCHON_ID) == 0){
                if(rc.getRoundNum() == 1 && rc.readBroadcast(ARCHON_ID) <= rc.getID()){
                    rc.broadcast(ARCHON_ID, rc.getID());
                }


                /*
                if (rc.senseRobotAtLocation(rc.getLocation().add(leftUp, spawnDist)) != null ) {
                    int newSpawnID = rc.senseRobotAtLocation(rc.getLocation().add(leftUp, spawnDist)).ID;



                }
                */

                //if(rc.getID() == rc.readBroadcast(ARCHON_ID)) {
                //System.out.println("take" + rc.isLocationOccupied(rc.getLocation().add(upRight, fiveTwo)));
                if(rc.getID() == rc.readBroadcast(ARCHON_ID) && rc.getRoundNum() != 1) {
                    if (rc.getTeamBullets() >= 100) {
                        if (rc.isLocationOccupied(rc.getLocation().add(leftUp, fiveTwo)) == true || !rc.canHireGardener(leftUp)) {
                            if (rc.isLocationOccupied(rc.getLocation().add(upRight, fiveTwo)) == true || !rc.canHireGardener(upRight)) {
                                if (rc.isLocationOccupied(rc.getLocation().add(rightDown, fiveTwo)) == true || !rc.canHireGardener(rightDown)) {
                                    if (rc.isLocationOccupied(rc.getLocation().add(downLeft, fiveTwo)) == true || !rc.canHireGardener(downLeft)) {
                                        if (rc.isLocationOccupied(rc.getLocation().add(leftDown, fiveTwo)) == true || !rc.canHireGardener(leftDown)) {
                                            if (rc.isLocationOccupied(rc.getLocation().add(upLeft, fiveTwo)) == true || !rc.canHireGardener(upLeft)) {
                                                if (rc.isLocationOccupied(rc.getLocation().add(rightUp, fiveTwo)) == true || !rc.canHireGardener(rightUp)) {
                                                    if (rc.isLocationOccupied(rc.getLocation().add(downRight, fiveTwo)) == true || !rc.canHireGardener(downRight)) {

                                                    } else {
                                                        tryHire(downRight,8);
                                                    }
                                                } else {
                                                    tryHire(rightUp,7);

                                                }
                                            } else {
                                                tryHire(upLeft,6);

                                            }
                                        } else {
                                            tryHire(leftDown,5);

                                        }
                                    } else {
                                        tryHire(downLeft,4);

                                    }
                                } else {
                                    tryHire(rightDown,3);

                                }
                            } else {
                                tryHire(upRight,2);
                            }
                        } else {
                            tryHire(leftUp,1);
                        }
                    }
                      if(rc.getTeamBullets() >= 90 && rc.getTreeCount() >= 12){
                          while (rc.getTeamBullets() >= 90) {
                              rc.donate((float)(7.5 + (rc.getRoundNum()*(12.5/3000))));
                          }
                      }
                      if (rc.getTeamBullets() >= 120 && rc.getRoundNum() >= 10) {
                          while (rc.getTeamBullets() >= 120){
                              rc.donate((float)(7.5 + (rc.getRoundNum()*(12.5/3000))));
                          }
                      }
                }

                if(rc.getID() != rc.readBroadcast(ARCHON_ID)){
                      dodge();
                }
                System.out.println(Clock.getBytecodesLeft());


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


                //Gard 1
                Direction up = new Direction(0,3);
                Direction leftUp = new Direction(-3,1);
                Direction leftDown2 = new Direction(-3,-2);
                //Gard 2
                Direction right = new Direction(3,0);
                Direction upRight = new Direction(1,3);
                Direction upLeft2 = new Direction(-2,3);
                //Gard 3
                Direction rightUp2 = new Direction(3,2);
                Direction rightDown = new Direction(3,-1);
                Direction down = new Direction(0,-3);
                //Gard 4
                Direction left = new Direction(-3,0);
                Direction downLeft = new Direction(-3,-1);
                Direction downRight2 = new Direction(2,-3);
                //Gard 5
                Direction leftDown = new Direction(-3,-1);
                //Gard 6
                Direction upLeft = new Direction(-1,3);
                //Gard 7
                Direction rightUp = new Direction( 3,1);
                //Gard 8
                Direction downRight = new Direction(1,-3);


                //float GardX=rc.getLocation().x;
                float ArcX = rc.senseRobot(rc.readBroadcast(ARCHON_ID)).location.x;
                float ArcY = rc.senseRobot(rc.readBroadcast(ARCHON_ID)).location.y;
                MapLocation home = new MapLocation(0,0);


                switch (rc.readBroadcast(IDScrub(rc.getID()))) {
                    case 1:
                        home = new MapLocation(ArcX - 5, ArcY + 2);
                        break;
                    case 2:
                        home = new MapLocation(ArcX + 2, ArcY + 5);
                        break;
                    case 3:
                        home = new MapLocation(ArcX + 5, ArcY - 2);
                        break;
                    case 4:
                        home = new MapLocation(ArcX - 2, ArcY - 5);
                        break;
                    case 5:
                        home = new MapLocation(ArcX - 5, ArcY - 2);
                        break;
                    case 6:
                        home = new MapLocation(ArcX - 2, ArcY + 5);
                        break;
                    case 7:
                        home = new MapLocation(ArcX + 5, ArcY + 2);
                        break;
                    case 8:
                        home = new MapLocation(ArcX + 2, ArcY - 5);
                        break;
                }

                    //boolean isHome = goHome(home);
                    if (goHome(home) == true) {

                        switch (rc.readBroadcast(IDScrub(rc.getID()))) {
                            case 1:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 5, ArcY - 5)) != null || !rc.canPlantTree(up)) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY)) != null || !rc.canPlantTree(leftDown2)) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY + 3)) != null || !rc.canPlantTree(leftUp)) {

                                        } else {
                                            tryPlant(leftUp);
                                        }
                                    } else {
                                        tryPlant(leftDown2);
                                    }
                                } else {
                                    tryPlant(up);
                                }
                                break;
                            case 2:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 5, ArcY + 5)) != null || !rc.canPlantTree(right)) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX , ArcY + 8)) != null || !rc.canPlantTree(upLeft2)) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX + 3, ArcY + 8)) != null || !rc.canPlantTree(upRight)) {

                                        } else {
                                            tryPlant(upRight);
                                        }
                                    } else {
                                        tryPlant(upLeft2);
                                    }
                                } else {
                                    tryPlant(right);
                                }
                                break;
                            case 3:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 5, ArcY - 5)) != null || !rc.canPlantTree(down)) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY)) != null || !rc.canPlantTree(rightUp2)) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY - 3)) != null || !rc.canPlantTree(rightDown)) {

                                        } else {
                                            tryPlant(rightDown);
                                        }
                                    } else {
                                        tryPlant(rightUp2);
                                    }
                                } else {
                                    tryPlant(down);
                                }
                                break;
                            case 4:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 5, ArcY - 5)) != null || !rc.canPlantTree(left)) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX, ArcY - 8)) != null || !rc.canPlantTree(downRight2)) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX - 3, ArcY - 8)) != null || !rc.canPlantTree(downLeft)) {

                                        } else {
                                            tryPlant(downLeft);
                                        }
                                    } else {
                                        tryPlant(downRight2);
                                    }
                                } else {
                                    tryPlant(left);
                                }
                                break;
                            case 5:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY - 3)) != null || !rc.canPlantTree(leftDown)) {

                                } else {
                                    tryPlant(leftDown);
                                }
                                break;
                            case 6:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 3, ArcY + 8)) != null || !rc.canPlantTree(upLeft)) {

                                } else {
                                    tryPlant(upLeft);
                                }
                                break;
                            case 7:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY + 3)) != null || !rc.canPlantTree(rightUp)) {

                                } else {
                                    tryPlant(rightUp);
                                }
                                break;
                            case 8:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 3, ArcY - 8)) != null || !rc.canPlantTree(downRight)) {

                                } else {
                                    tryPlant(downRight);
                                }
                                break;






                        }
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

                //if(rc.getRoundNum() == 2){
                if(rc.getRoundNum() == 3){
                        rc.buildRobot(RobotType.SCOUT,Direction.SOUTH);
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
                    if (p.getContainedBullets() != 0) {
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

                Clock.yield();

            } catch (Exception e) {
                System.out.println("Lumberjack Exception");
                e.printStackTrace();
            }
        }
    }

    public static void wander() throws GameActionException {
        Direction dir = randomDirection();
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
        for (BulletInfo bi : bullets) {
            if (willCollideWithMe(bi)) {
                trySidestep(bi);
            }
        }

    }

}

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
                float fourOne = (float)Math.sqrt(17.0);
                float spawnDist = (float)Math.sqrt(9.06949);


                if(rc.getRoundNum() == 1 && rc.readBroadcast(ARCHON_ID) == 0){
                    rc.broadcast(ARCHON_ID, rc.getID());
                }


                /*
                if (rc.senseRobotAtLocation(rc.getLocation().add(leftUp, spawnDist)) != null ) {
                    int newSpawnID = rc.senseRobotAtLocation(rc.getLocation().add(leftUp, spawnDist)).ID;



                }
                */


                  if(rc.getID() == rc.readBroadcast(ARCHON_ID)) {
                    if (rc.getTeamBullets() >= 100) {
                        if (rc.senseRobotAtLocation(rc.getLocation().add(leftUp, fiveTwo)) != null) {
                            if (rc.senseRobotAtLocation(rc.getLocation().add(upRight, fiveTwo)) != null) {
                                if (rc.senseRobotAtLocation(rc.getLocation().add(rightDown, fiveTwo)) != null) {
                                    if (rc.senseRobotAtLocation(rc.getLocation().add(downLeft, fiveTwo)) != null) {
                                        if (rc.senseRobotAtLocation(rc.getLocation().add(leftDown, fiveTwo)) != null) {
                                            if (rc.senseRobotAtLocation(rc.getLocation().add(upLeft, fiveTwo)) != null) {
                                                if (rc.senseRobotAtLocation(rc.getLocation().add(rightUp, fiveTwo)) != null) {
                                                    if (rc.senseRobotAtLocation(rc.getLocation().add(downRight, fiveTwo)) != null) {
                                                        if(rc.getTeamBullets() >= 120 && rc.getTreeCount() >= 8){
                                                            while (rc.getTeamBullets() >= 120) {
                                                                rc.donate(10);
                                                            }
                                                        }
                                                        if (rc.getTeamBullets() >= 150) {
                                                            while (rc.getTeamBullets() >= 150){
                                                                rc.donate(10);
                                                            }
                                                        }
                                                    } else {
                                                        rc.hireGardener(downRight);
                                                        rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(downRight, spawnDist)).getID()), 8);
                                                    }
                                                } else {
                                                    rc.hireGardener(rightUp);
                                                    rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(rightUp, spawnDist)).getID()), 7);
                                                }
                                            } else {
                                                rc.hireGardener(upLeft);
                                                rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(upLeft, spawnDist)).getID()), 6);
                                            }
                                        } else {
                                            rc.hireGardener(leftDown);
                                            rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(leftDown, spawnDist)).getID()), 5);
                                        }
                                    } else {
                                        rc.hireGardener(downLeft);
                                        rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(downLeft, spawnDist)).getID()), 4);
                                    }
                                } else {
                                    rc.hireGardener(rightDown);
                                    rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(rightDown, spawnDist)).getID()), 3);
                                }
                            } else {
                                rc.hireGardener(upRight);
                                rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(upRight, spawnDist)).getID()), 2);
                            }
                        } else {
                            rc.hireGardener(leftUp);
                            rc.broadcast(IDScrub(rc.senseRobotAtLocation(rc.getLocation().add(leftUp, spawnDist)).getID()), 1);
                        }
                    }
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
                Direction rightUp2 = new Direction(3,-2);
                Direction rightDown = new Direction(3,-1);
                Direction down = new Direction(0,-3);
                //Gard 4
                Direction left = new Direction(-3,0);
                Direction downLeft = new Direction(-3,-1);
                Direction downRight2 = new Direction(-3,2);
                //Gard 5
                Direction leftDown = new Direction(-3,-1);
                //Gard 6
                Direction upLeft = new Direction(-1,3);
                //Gard 7
                Direction rightUp = new Direction( 3,1);
                //Gard 8
                Direction downRight = new Direction(1,-3);


                float GardX=rc.getLocation().x;
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

                    boolean isHome = goHome(home);
                    if (isHome == true) {

                        switch (rc.readBroadcast(IDScrub(rc.getID()))) {
                            case 1:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY)) != null) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY + 3)) != null) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX - 5, ArcY + 5)) != null) {

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
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX, ArcY + 8)) != null) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX + 3, ArcY + 8)) != null) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX + 5, ArcY + 5)) != null) {

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
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY)) != null) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY - 3)) != null) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX + 5, ArcY - 5)) != null) {

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
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX, ArcY - 8)) != null) {
                                    if (rc.senseTreeAtLocation(new MapLocation(ArcX - 3, ArcY - 8)) != null) {
                                        if (rc.senseTreeAtLocation(new MapLocation(ArcX - 5, ArcY - 5)) != null) {

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
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 8, ArcY - 3)) != null) {

                                } else {
                                    tryPlant(leftDown);
                                }
                                break;
                            case 6:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX - 3, ArcY + 8)) != null) {

                                } else {
                                    tryPlant(upLeft);
                                }
                                break;
                            case 7:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 8, ArcY + 3)) != null) {

                                } else {
                                    tryPlant(rightUp);
                                }
                                break;
                            case 8:
                                if (rc.senseTreeAtLocation(new MapLocation(ArcX + 3, ArcY - 8)) != null) {

                                } else {
                                    tryPlant(downRight);
                                }
                                break;






                        }
                        TreeInfo[] trees = rc.senseNearbyTrees(2);
                        for(TreeInfo t : trees){
                            System.out.println(t.getID());
                            if(rc.canWater(t.getID())) {
                                if (t.getHealth() < t.getMaxHealth()) {
                                    rc.water(t.getID());
                                    System.out.println(t.getHealth());
                                    System.out.println(t.getMaxHealth());
                                }
                            }
                        }
                    }


                if(rc.getRoundNum() == 2){
                        rc.buildRobot(RobotType.SCOUT,Direction.NORTH);
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
                TreeInfo[] trees = rc.senseNearbyTrees();
                for(TreeInfo t : trees){
                    if(rc.canShake(t.getLocation())){
                        rc.shake(t.getLocation());
                    }
                }
                wander();
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
        int cleanID = ID % 1000;
        return cleanID;
    }

    public static boolean goHome(MapLocation homeLoc) throws GameActionException {
        if (rc.getLocation().x != homeLoc.x && rc.getLocation().y != homeLoc.y) {
            rc.move(homeLoc);
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

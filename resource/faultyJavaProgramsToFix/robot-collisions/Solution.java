import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Solution {
    public List<Integer> survivedRobotsHealths(int[] positions, int[] healths, String directions) {
        final int n = positions.length;
        List<Robot> robotList = new ArrayList<>(n);

        for (int idx = 0; idx < n; idx++) {
            robotList.add(new Robot(
                positions[idx], 
                healths[idx], 
                directions.charAt(idx), 
                idx
            ));
        }

        Collections.sort(robotList, (a, b) -> a.position - b.position);

        Deque<Robot> processingStack = new ArrayDeque<>();

        for (Robot current : robotList) {
            if (shouldPushToStack(processingStack, current)) {
                processingStack.push(current);
                continue;
            }

            if (current.direction == 'L') {
                boolean shouldAddCurrent = true;
                
                while (!processingStack.isEmpty() && 
                       processingStack.peek().direction == 'R' && 
                       shouldAddCurrent) {
                    
                    Robot top = processingStack.peek();
                    if (current.health > top.health) {
                        processingStack.pop();
                        current.health--;
                    } else if (current.health < top.health) {
                        top.health--;
                        shouldAddCurrent = false;
                    } else {
                        processingStack.pop();
                        shouldAddCurrent = false;
                    }

                    if (shouldAddCurrent) {
                        processingStack.push(current);
                    }
                }
            }
        }

        List<Robot> remaining = new ArrayList<>(processingStack);
        remaining.sort((a, b) -> a.index - b.index);

        List<Integer> finalHealths = new ArrayList<>();
        for (Robot robot : remaining) {
            finalHealths.add(robot.health);
        }

        return finalHealths;
    }

    private boolean shouldPushToStack(Deque<Robot> stack, Robot robot) {
        return robot.direction == 'R' || stack.isEmpty() || stack.peek().direction == 'L';
    }

    private static class Robot {
        final int position;
        int health;
        final char direction;
        final int index;

        Robot(int position, int health, char direction, int index) {
            this.position = position;
            this.health = health;
            this.direction = direction;
            this.index = index;
        }
    }
}
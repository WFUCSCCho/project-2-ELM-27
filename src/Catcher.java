/*
 * @file: Catcher.java
 * @description: This class creates the data type Catcher, which hold 4 kinds of data:
 *               String username: The username of the player
 *               int userID:      The player's account ID
 *               int skill:       Combination of various user performance statistics
 *               String title:    Title given based off of skill
 * @author: Elliott Lowman
 * @date: October 24, 2024
 */

import java.io.*;
import java.util.*;

public class Catcher implements Comparable<Catcher> {

    // Variable Definition
    private String username;
    private int userID;
    private int skill;
    private String title;

    // for manual declaration
    public Catcher(String username, int userID, int skill, String title) {
        this.username = username;
        this.userID = userID;
        this.skill = skill;
        this.title = title;
    }

    // for declaration that searches CTB_Data.csv
    public Catcher(int userID) throws IOException {
        FileReader inputFile = new FileReader("CTB_Data.csv");
        Scanner sc = new Scanner(inputFile);
        String testString;
        String temp;
        String temp2;
        int index;
        boolean found = false;

        this.userID = userID;

        while(sc.hasNextLine()) {
            testString = sc.nextLine();
            if(testString.contains(Integer.toString(userID))) {
                // Ensures the input and userID match
                index = testString.indexOf(',');
                temp = testString.substring(index + 1);
                index = temp.indexOf(',');
                temp = temp.substring(0, index);
                if(Integer.parseInt(temp) == userID) {
                    found = true;

                    // Assigns username
                    index = testString.indexOf(',');
                    // Isolate username
                    temp = testString.substring(0, index);
                    // Set username
                    this.username = temp;

                    // Assigns skill rating
                    // Navigate to skill rating
                    temp = testString.substring(index + 1);
                    index = temp.indexOf(',');
                    temp = temp.substring(index + 1);
                    index = temp.indexOf(',');
                    // Isolate title
                    temp2 = temp.substring(index + 1);
                    // Isolate skill rating
                    temp = temp.substring(0, index);
                    // Set skill rating
                    this.skill = Integer.parseInt(temp);

                    // Set title
                    this.title = temp2;
                }
            }
        }

        // user not found
        if(!found) {
            this.userID = 0;
        }

        inputFile.close();
    }

    // for directly taking from CTB_Data.csv
    public Catcher(String fullString) {
        username = fullString.substring(0, fullString.indexOf(","));
        fullString = fullString.substring(fullString.indexOf(",") + 1);
        userID = Integer.parseInt(fullString.substring(0, fullString.indexOf(",")));
        fullString = fullString.substring(fullString.indexOf(",") + 1);
        skill = Integer.parseInt(fullString.substring(0, fullString.indexOf(",")));
        fullString = fullString.substring(fullString.indexOf(",") + 1);
        title = fullString;
    }

    public int getSkill() {
        return skill;
    }

    public String getUsername() {
        return username;
    }

    // used only when a Catcher object is being printed
    @Override
    public String toString() {
        String returnString = "Username: " + username;
        returnString = returnString.concat(", User ID: " + userID);
        returnString = returnString.concat(", Skill: " + skill);
        returnString = returnString.concat(", Title: " + title);
        return returnString;
    }

    @Override
    public int compareTo(Catcher c) {
        int result;

        result = skill - c.getSkill();

        if(result == 0) {
            result = username.compareTo(c.getUsername());
        }

        return result;
    }
}
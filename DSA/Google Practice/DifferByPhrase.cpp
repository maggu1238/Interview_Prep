/*
Given two strings s1 and s2, find out if they only differ by the insertion of a phrase.
*/

/*
Example:


The boy goes to the hospital
The cute little boy goes to the hospital
'cute litte' is the added phrase everything else is the same so return True


Example:


The boy is nice.
The girl is nice.
-> Return False
*/

// in java

public static boolean areSeparatedByInsertionPhrase(String s1, String s2) {
    String[] words1 = s1.split(" ");
    String[] words2 = s2.split(" ");

    int l1 = -1, l2 = -1;
    int r1 =  words1.length, r2 = words2.length;

    if(r1 == r2) {
        return false;
    }

    boolean isMatchFound = true;
    while(isMatchFound && l1 != r1-1 && l2 != r2-1) {
        isMatchFound = false;
        if(words1[l1 + 1].equals(words2[l2 + 1])) {
            l1++;
            l2++;
            isMatchFound = true;
        }

        if(words1[r1 - 1].equals(words2[r2 - 1])) {
            r1--;
            r2--;
            isMatchFound = true;
        }
    }

    return l1 == r1-1 || l2 == r2-1;
}


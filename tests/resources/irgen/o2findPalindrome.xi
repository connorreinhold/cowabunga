use io
use conv

// https://leetcode.com/problems/longest-palindromic-substring/
longestPalindrome(s: int[]): int[] {
    if (length(s) == 0) {
        return "";
    }
    
    isPalindrome: bool[length(s)][length(s)];
    longestStart: int = 0;
    longestEnd: int = 0;
    
    i: int = 0;
    size: int = length(s) - 1;
    while (i < size) {
        isPalindrome[i][i] = true;
        isPalindrome[i][i + 1] = s[i] == s[i + 1]
        if (isPalindrome[i][i + 1]) {
            longestStart = i;
            longestEnd = i + 1;
        }
        i = i + 1;
    }
    isPalindrome[length(s) - 1][length(s) - 1] = true;
    lengthToCheck: int = 3;
    while (lengthToCheck <= length(s)) {
        i = 0;
        size = length(s)
        while (i + lengthToCheck - 1 < size) {
            j: int = i + lengthToCheck - 1;
            isPalindrome[i][j] = isPalindrome[i + 1][j - 1] & s[i] == s[j];
            if (isPalindrome[i][j] & j - i > longestEnd - longestStart) {
                longestStart = i;
                longestEnd = j;
            }
            i = i + 1;
        }
        lengthToCheck = lengthToCheck + 1;
    }
    newString: int[longestEnd + 1 - longestStart];
    i = longestStart
    size = longestEnd + 1
    while (i < size) {
        newString[i - longestStart] = s[i]
        i = i + 1
    }
    return newString;
}

main(args: int[][]) {
    println(longestPalindrome("Hello World"))
    println(longestPalindrome("racecar"))
    println(longestPalindrome("Ok Bye"))
}
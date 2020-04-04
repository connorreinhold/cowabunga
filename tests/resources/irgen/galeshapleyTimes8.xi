use io
use conv

main(args: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(importData(args))
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

importData(prefData: int[][]): int[][] {
    i: int = 0
    lengthPref: int = length(prefData)
    data: int[lengthPref][lengthPref/2]
    while i < lengthPref {
        j: int = 0
        start: int = 0
        lengthOfI: int = length(prefData[i])
        while start < lengthOfI {
            end: int = start
            while end < lengthOfI & prefData[i][end] != ' '{
                end = end + 1
            }
            sizeOfNum: int = end - start
            numString: int[sizeOfNum]
            k: int
            while k < sizeOfNum {
                numString[k] = prefData[i][start+k]
                k = k + 1
            }
            num: int, _ = parseInt(numString)
            data[i][j] = num
            start = end + 1
            j = j + 1
        }
        i = i + 1
    }
    return data
}

prefs(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main2(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs2(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley2(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer2(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray2(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray2(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main_1(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs_1(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley_1(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer_1(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray_1(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray_1(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main2_1(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs2_1(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley2_1(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer2_1(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray2_1(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray2_1(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main_20(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs_20(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley_20(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer_20(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray_20(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray_20(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main2_20(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs2_20(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley2_20(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer2_20(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray2_20(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray2_20(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main_1_20(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs_1_20(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley_1_20(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer_1_20(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray_1_20(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray_1_20(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

main2_1_20(prefData: int[][]) {
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs2_1_20(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2
    
    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];
    
    i: int = 0
    j: int = 0
    
    while i < n {
        j = 0
        while j < n {
            proposerPrefs[i][j] = prefData[i][j]
            j = j + 1
        }
        i = i + 1
    }
    
    i = 0
    while i < n {
        j = 0
        line: int[] = prefData[n + i]
        while j < n {
            respondentInvPrefs[i][line[j]] = j
            j = j + 1
        }
        i = i + 1
    }
    
    return proposerPrefs, respondentInvPrefs
}

galeShapley2_1_20(proposerPrefs: int[][], respondentPrefsInv: int[][]): int[] {
    n: int = length(proposerPrefs)
    
    i: int
    j: int
    
    matchRespondentToProposer: int[n]
    
    i = 0
    while i < n {
        matchRespondentToProposer[i] = -1
        i = i + 1
    }
    
    proposerPreferenceIndex: int[n]
    
    i = 0
    while i < n {
        proposerPreferenceIndex[i] = 0
        i = i + 1
    }
    
    matched: bool[n]
    i = 0
    while i < n {
        matched[i] = false
        i = i + 1
    }
    
    allMatched: bool = false
    while (!allMatched) {
        allMatched = true
        proposer: int = 0
        while proposer < n {
            if !matched[proposer] {
                matched[proposer] = true
                
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1
                
                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {
                        matched[proposer] = false
                        } else {
                        matchRespondentToProposer[respondent] = proposer
                        matched[otherProposer] = false
                    }
                }
                
                allMatched = false
            }
            proposer = proposer + 1
        }
    }
    
    return matchRespondentToProposer
}

respondentPrefersProposer2_1_20(respondentPrefsInv: int[][], respondent: int, leftProposer: int, rightProposer: int): bool {
    leftPref: int = respondentPrefsInv[respondent][leftProposer]
    rightPref: int = respondentPrefsInv[respondent][rightProposer]
    return leftPref < rightPref
}

printArray2_1_20(a: int[]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        if (i < length(a) - 1)
        print(", ")
        i = i + 1
    }
    print("]")
}

printArrayArray2_1_20(a: int[][]) {
    i: int = 0
    print("[")
    while (i < length(a)) {
        printArray(a[i])
        i = i + 1
    }
    print("]")
}

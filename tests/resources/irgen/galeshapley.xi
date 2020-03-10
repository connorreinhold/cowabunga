use io
use conv

main(prefData: int[][]) {
    i: int = 0
    while (i < length(prefData)) {
        printArray(prefData[i])
        i = i + 1
    }
    proposerPrefs: int[][], respondentInvPrefs: int[][] = prefs(prefData)
    result: int[] = galeShapley(proposerPrefs, respondentInvPrefs)
    printArray(result)
}

prefs(prefData: int[][]): int[][], int[][] {
    n: int = length(prefData) / 2

    proposerPrefs: int[n][n];
    respondentInvPrefs: int[n][n];

    i: int = 0
    j: int = 0

    while i < n {
        while j < n {
           proposerPrefs[i][j] = prefData[i][j]
           j = j + 1
        }
        i = i + 1
    }

    i = 0
    while i < n {
        while j < n {
            respondentInvPrefs[i][prefData[i][j]] = j
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
                respondent: int = proposerPrefs[proposer][proposerPreferenceIndex[proposer]]
                proposerPreferenceIndex[proposer] = proposerPreferenceIndex[proposer] + 1

                otherProposer: int = matchRespondentToProposer[respondent]
                if otherProposer == -1
                    matchRespondentToProposer[respondent] = proposer
                else {
                    if respondentPrefersProposer(respondentPrefsInv, respondent, otherProposer, proposer) {

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
    print("[ ")
    while (i < length(a)) {
        print(unparseInt(a[i]))
        print(" ")
        i = i + 1
    }
    print("]")
}


def generate_banner(text):
    letters = {
    'A': [
        "  A  ",
        " A A ",
        "AAAAA",
        "A   A",
        "A   A",
    ],
    'B': [
        "BBB ",
        "B  B",
        "BBB ",
        "B  B",
        "BBB ",
    ],
    'E': [
        "EEE",
        "E  ",
        "EEE",
        "E  ",
        "EEE",
    ],
    'I': [
        "III",
        " I ",
        " I ",
        " I ",
        "III",
    ],
    'L': [
        "L  ",
        "L  ",
        "L  ",
        "L  ",
        "LLL",
    ],
    'M': [
        "M   M",
        "MM MM",
        "M M M",
        "M   M",
        "M   M",
    ],
    'R':[
        "RRRR ",
        "R   R",
        "RRRR ",
        "R R  ",
        "R  RR",
        ],
    'N': [
        "N   N",
        "NN  N",
        "N N N",
        "N  NN",
        "N   N",
    ],
    'W': [
        "W   W",
        "W   W",
        "W W W",
        "WW WW",
        "W   W",
    ],
    'O': [
        "OOO",
        "O O",
        "O O",
        "O O",
        "OOO",
    ],
    'T': [
        "TTT",
        " T ",
        " T ",
        " T ",
        " T ",
    ],
    'P': [
        "PPP ",
        "P  P",
        "PPP ",
        "P   ",
        "P   ",
    ],
    'S': [
        " SSS",
        "S   ",
        " SSS",
        "   S",
        "SSS ",
    ],
    }

    lines = [""] * 5
    for char in text:
        char_lines = letters.get(char.upper(), ["     "] * 5)
        for i in range(5):
            lines[i] += char_lines[i] + "  "

    return "\n".join(lines)

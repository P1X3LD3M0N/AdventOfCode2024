function defDict(initialValue) {
    var dict = {};
    return {
        get: function (key) {
            if (!dict[key]) {
                dict[key] = Array.isArray(initialValue) ? [] : initialValue;
            }
            return dict[key];
        },
        dict: dict
    };
}


function part1(pages, updates) {
    let req_pages = defDict([]);
    let sum = 0;

    for (let i = 0; i < pages.length; i++) {
        req_pages.get(pages[i][1]).push(pages[i][0]);
    }

    for (let urow of updates) {
        let needs_first = new Set();
        let is_valid = true;

        for (let u of urow) {
            if (needs_first.has(u)) {
                is_valid = false;
                break;
            }
            needs_first = new Set([...needs_first, ...req_pages.get(u)]);
        }

        if (is_valid) {
            const middleIndex = Math.floor(urow.length / 2);
            sum += urow[middleIndex];
        }
    }

    return sum;
}

function part2(pages, updates) {
    let req_pages = defDict([]);
    let sum = 0;

    for (let i = 0; i < pages.length; i++) {
        req_pages.get(pages[i][1]).push(pages[i][0]);
    }

    for (let urow of updates) {
        let needs_first = new Set();
        let is_valid = true;

        for (let u of urow) {
            if (needs_first.has(u)) {
                is_valid = false;
                break;
            }
            needs_first = new Set([...needs_first, ...req_pages.get(u)]);
        }

        if (!is_valid) {
                let sorted = [...urow].sort((a, b) => {
                if (req_pages.get(b).includes(a)) return -1;
                if (req_pages.get(a).includes(b)) return 1;
                return 0;
            });

            const middle = Math.floor(sorted.length / 2);
            sum += sorted[middle];
        }
    }

    return sum;
}



const fs = require('fs');

fs.readFile('input.txt', 'utf8', (err, data) => {
    const parts = data.split(/\r?\n\s*\r?\n/);

    if (parts.length < 2) {
        console.error("File format is not as expected. Could not split into two parts.");
        return;
    }

    const p1 = parts[0];
    const p2 = parts[1];

    const array1 = p1.split(/\r?\n/).map(line => 
        line.split('|').map(Number)
    );

    const array2 = p2.split(/\r?\n/).map(line => 
        line.split(',').map(Number)
    );

    console.log("Part 1:", part1(array1, array2));
    console.log("Part 2:", part2(array1, array2));
});

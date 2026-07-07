const output = document.getElementById("output");

function println(line) {
    output.value += line;
    output.value += "\n";
}

function println2(line) {
    output.value += line;
    output.value += "\n\n";
}


function demonstrateConcurrency() {
    println2("Will it take 10 seconds, or less? (Try multiple times)");
    const before = Date.now();
    let countDown =     10;
    for (let i = 0; i < 10; ++i) {
        fetch("/threads/current")
        .then(response => response.text())
        .then(println)
        .catch(println2)
        .finally(() => {
            if (--countDown === 0) {
                const after = Date.now();
                const seconds = (after - before) / 1e3;
                println2(`${seconds} seconds`);
            }
        });
    }
}


function demonstrateDataRace() {
    println2("Will every array element be its index?");
    const wrongPositions = new Set();
    for (let i = 0; i < 100; ++i) {
        fetch("/threads/race")
        .then(response => response.text().then(text => {
            if (response.status !== 200) {
                println2(text);
            } else {
                println(text);
                const array = JSON.parse(text);
                for (let i = 0; i < array.length; ++i) {
                    if (array[i] !== i) {
                        if (wrongPositions.has(i)) continue;
                        println2(`expected ${i} but found ${array[i]}`);
                        wrongPositions.add(i);
                    }
                }
            }
        }))
        .catch(println2);
    }
}


function postRandomBooks() {
    println2("Will every array size appear once?");
    for (let i = 0; i < 50; ++i) {
        fetch("/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept":       "application/json",
            },
            body: JSON.stringify({
                title:  "Title",
                author: "Author",
                year:   1900 + Math.floor(Math.random() * 100),
            })
        })
        .then(response => response.json())
        .then(books => books.map(book => book.year))
        .then(years => println(`${years.length} ${JSON.stringify(years)}`))
        .catch(println2);
    }
}

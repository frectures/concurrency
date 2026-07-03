function demonstrateConcurrency() {
    // Will it take 10 seconds, or less?"
    for (let i = 0; i < 10; ++i) {
        fetch("/threads/current")
        .then(response => response.text())
        .then(name => console.log(name))
        .catch(error => console.log(error));
    }
}

demonstrateConcurrency();


function demonstrateRaceCondition() {
    // Will every array size between 3 and 12 appear once?
    for (let i = 0; i < 10; ++i) {
        fetch("/books", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept":       "application/json",
            },
            body: JSON.stringify({
                title:  "Random Title",
                author: "Random Author",
                year:   1900 + Math.floor(Math.random() * 100),
            })
        })
        .then(response => response.json())
        .then(books => console.log(books))
        .catch(error => console.log(error));
    }
}

// demonstrateRaceCondition();

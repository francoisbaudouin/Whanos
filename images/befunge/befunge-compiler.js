const Befunge = require('befunge93');
const fs = require('fs')

let befunge = new Befunge();
let filepath = process.argv[2]

if (process.argv.length != 3) {
    console.error("Bad args, try with node befunge-compiler.js [file]")
    return
}

try {
    if (fs.existsSync(filepath)) {
        befunge.onInput = (message) => {
            process.stdout.write(message);
            return fs.readFileSync(process.stdin.fd).toString();
        };
        befunge.onOutput = (output) => {
            process.stdout.write(output);
        };
        befunge.run(fs.readFileSync(filepath).toString());
    } else {
        return;
    }
} catch(err) {
    console.error("error happened", err)
}
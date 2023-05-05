const { defineConfig } = require("cypress");

module.exports = defineConfig({
  e2e: {
    setupNodeEvents(on, config) {
      on("before:browser:launch", (browser, launchOptions) => {
        // set the baseUrl configuration option
        launchOptions.args.push("--baseUrl=http://localhost:5173");
        return launchOptions;
      });
    },
  },
});
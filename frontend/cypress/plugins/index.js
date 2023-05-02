const { startDevServer } = require('@cypress/webpack-dev-server');
const webpackConfig = require('@vue/cli-service/webpack.config');
const codeCoverageTask = require('@cypress/code-coverage/task');

module.exports = (on, config) => {
    on('dev-server:start', (options) =>
        startDevServer({ options, webpackConfig })
    );

    // Register the code coverage task
    codeCoverageTask(on, config);

    // Important: return the updated config object
    return config;
};

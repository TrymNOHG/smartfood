const isCoverageEnabled = process.env.COVERAGE === 'true';

module.exports = {
    configureWebpack: {
        module: {
            rules: isCoverageEnabled
                ? [
                    {
                        test: /\.js$/,
                        loader: 'babel-loader',
                        include: require('path').join(__dirname, 'src'),
                        exclude: /node_modules/,
                        options: {
                            plugins: ['istanbul'],
                        },
                    },
                    {
                        test: /\.vue$/,
                        loader: 'vue-loader',
                        options: {
                            loaders: {
                                js: {
                                    loader: 'babel-loader',
                                    options: {
                                        plugins: ['istanbul'],
                                    },
                                },
                            },
                        },
                    },
                ]
                : [],
        },
    },
    pluginOptions: {
        cypress: {
            testingType: 'e2e',
        },
    },
};

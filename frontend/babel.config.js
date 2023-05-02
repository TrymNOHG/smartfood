module.exports = {
    presets: [
        '@vue/cli-plugin-babel/preset'
    ],
    plugins: [
        // Add the following line to include the babel-plugin-istanbul
        process.env.NODE_ENV === 'test' ? 'babel-plugin-istanbul' : null
    ].filter(Boolean)
};

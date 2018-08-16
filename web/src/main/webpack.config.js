var webpack = require('webpack');
var path = require('path');
var OpenBrowserPlugin = require('open-browser-webpack-plugin');

module.exports = {
  devtool: 'eval-source-map',
  devServer: {
    /* headers: {
       'Access-Control-Allow-Origin': '*',
       'Access-Control-Allow-Headers': 'Origin, X-Requested-With, Content-Type, Accept'
     },*/
    historyApiFallback: true,
    hot: true,
    inline: true,
    contentBase: path.join(__dirname, 'webapp'),
    progress: true,
    port: 8000,
    proxy: {
      '/api': {
        // target: 'http://192.168.1.136:8080/',
        target: 'http://192.168.1.141:8080/',
          // target: 'http://123.206.6.152:8082/',
        changeOrigin: true,
        pathRewrite: {
          '^/api': ''
        }
      },
      '/modules': {
          // target: 'http://192.168.1.136:8080/',
          target: 'http://192.168.1.141:8080/',
          // target: 'http://123.206.6.152:8082/',
          changeOrigin: true
      }
    }
  },
  entry: [
    'webpack-dev-server/client?http://localhost:8000',
    'webpack/hot/dev-server',
    path.resolve(__dirname, 'react/main.js')
  ],
  output: {
    path: __dirname + '/webapp/dev',
    publicPath: '/',
    filename: './bundle.js',
    chunkFilename: '[id].bundle.js'
  },
  module: {
    loaders: [
      {
        test: /\.css$/,
        loader: 'style-loader!css-loader'
      },
      {
        test: /\.js[x]?$/,
        include: path.resolve(__dirname, 'react'),
        exclude: /node_modules/,
        loaders: ['react-hot', 'babel']
      },
      {
        test: /\.(png|jpg)$/,
        loader: 'url-loader?limit=8192'
      },
      {
        test: /\.(woff|woff2|eot|ttf|svg)(\?.*$|$)/,
        loader: 'url'
      }
    ]
  },
  resolve: {
    extensions: ['', '.js', '.jsx']
  },
  plugins: [
    new webpack.DefinePlugin({
      'process.env': { mode: '"dev"' }
    }),
    new OpenBrowserPlugin({
      url: 'http://localhost:8000/index.html'
    })
  ]
}

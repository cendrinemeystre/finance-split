const FINANCE_SPLIT_SERVER = process.env.FINANCE_SPLIT_SERVER || 'http://localhost:8080';

const PROXY_CONFIG = [
  {
    context: ['/api'],
    target: FINANCE_SPLIT_SERVER,
    secure: false
  }
];

module.exports = PROXY_CONFIG;

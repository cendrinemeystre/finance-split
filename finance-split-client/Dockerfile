# Stage 1: Build Angular application
FROM node:24 AS builder
WORKDIR /finance-split-client

# Copy package files and install dependencies
COPY package.json package-lock.json ./
RUN npm install

# Copy all source code and build production version
COPY . .
RUN npm run build -- --configuration production

# Create needed root dir
FROM nginx:alpine
RUN mkdir -p \
    /var/cache/nginx/client_temp \
    /var/cache/nginx/proxy_temp \
    /var/cache/nginx/fastcgi_temp \
    /var/cache/nginx/uwsgi_temp \
    /var/cache/nginx/scgi_temp \
    && chown -R nginx:nginx /var/cache/nginx \
    && chown -R nginx:nginx /var/run \
    && chown -R nginx:nginx /usr/share/nginx/html

# Change PID location to /tmp/nginx.pid to avoid /run permissions
RUN sed -i 's|^pid .*;|pid /tmp/nginx.pid;|' /etc/nginx/nginx.conf

COPY nginx.conf /etc/nginx/conf.d/default.conf

# Serve Angular application using nginx
USER nginx
COPY --from=builder /finance-split-client/dist/finance-split-client/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]

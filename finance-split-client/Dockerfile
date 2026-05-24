FROM node:20-alpine AS build
WORKDIR /finance-split-client
COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build -- --configuration production
# Angular outputs to dist/<project-name> — adjust to match yours
FROM nginx:alpine
COPY --from=build /finance-split-client/target/build-angular/finance-split-client/browser /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80

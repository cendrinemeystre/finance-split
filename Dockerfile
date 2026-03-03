LABEL authors="Cendrine"
# Dockerfile

# Stage 1: Build Angular application
FROM node AS builder
WORKDIR /finance-split-client
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve Angular application using nginx
FROM nginx:alpine
COPY --from=builder /finance-split-client/dist/finance-split-client/browser /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
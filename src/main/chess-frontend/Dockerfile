## Build Frontend
FROM node:20-alpine AS build
WORKDIR /app

# Copy package files and install dependencies
COPY package*.json ./
RUN npm install

# Copy source code
COPY ../../.. .

# Build Angular app
RUN npm run build -- --configuration production

# Serve app
FROM nginx:alpine
WORKDIR /usr/share/nginx/html

# Copy built app from builder
COPY --from=build /app/dist/chess-frontend /usr/share/nginx/html

# Copy custom Nginx config
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 4200

# Start server
CMD ["nginx", "-g", "daemon off;"]

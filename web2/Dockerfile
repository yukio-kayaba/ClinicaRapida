##
## ClinicaRapida / web2 - Vite + React
##
## Targets:
##  - dev  : hot-reload (vite)
##  - build: generates /app/dist
##  - prod : nginx serving /usr/share/nginx/html
##

# ---- deps ----
FROM node:20-alpine AS deps
WORKDIR /app
COPY package.json package-lock.json ./
RUN npm ci

# ---- dev ----
FROM node:20-alpine AS dev
WORKDIR /app
ENV NODE_ENV=development
COPY --from=deps /app/node_modules ./node_modules
COPY . .
EXPOSE 5173
# Note: we keep 0.0.0.0 so it works from Docker/WSL/remote hosts
CMD ["npm","run","dev","--","--host","0.0.0.0","--port","5173"]

# ---- build ----
FROM node:20-alpine AS build
WORKDIR /app
ENV NODE_ENV=production
COPY --from=deps /app/node_modules ./node_modules
COPY . .
RUN npm run build

# ---- prod (nginx) ----
FROM nginx:1.27-alpine AS prod
COPY --from=build /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx","-g","daemon off;"]



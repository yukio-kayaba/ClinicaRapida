# ClinicaRapida - web2 (Vite + React + TS)

## Requisitos

- Node.js 20+ (para desarrollo local)
- Docker Desktop (para correr con contenedores)

## Desarrollo (local)

```bash
npm install
npm run dev
```

Por defecto Vite usa `http://localhost:5173`.

## Desarrollo (Docker + docker compose)

Levanta Vite con hot-reload dentro del contenedor:

```bash
docker compose up --build
```

Abrir: `http://localhost:5173`

> Nota: el contenedor corre Vite con `--host 0.0.0.0` para que sea accesible desde tu host.

## Producción (Docker)

Build multi-stage y servir `dist/` con Nginx:

```bash
docker build -t clinicarapida-web2:prod --target prod .
docker run --rm -p 8080:80 clinicarapida-web2:prod
```

Abrir: `http://localhost:8080`

## Archivos Docker incluidos

- `Dockerfile`
  - target `dev`: `npm run dev -- --host 0.0.0.0 --port 5173`
  - target `prod`: build y Nginx sirviendo estáticos
- `docker-compose.yml`: flujo de desarrollo con bind mount
- `.dockerignore`: acelera el build evitando `node_modules`, `dist`, etc.


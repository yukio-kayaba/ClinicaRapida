# Sistema de Enfermería

Sistema frontend para gestión de personal médico desarrollado con React + TypeScript + Vite.

## Características

- ✅ Arquitectura escalable y modular
- ✅ Separación de lógica y UI
- ✅ Context API para manejo de estado
- ✅ Sincronización con localStorage
- ✅ Diseño moderno y minimalista estilo hospital
- ✅ Componentes reutilizables
- ✅ Preparado para futura integración con WebSockets

## Estructura del Proyecto

```
src/
  types/
    Personal.ts              # Tipos TypeScript
  context/
    PersonalContext.tsx      # Context API con estado y localStorage
  components/
    personalView/
      PersonalCard.tsx       # Card individual de personal
      ViewPersonal.tsx       # Vista principal con grid
  App.tsx                    # Componente raíz
  main.tsx                   # Punto de entrada
  index.css                  # Estilos globales con TailwindCSS
```

## Instalación

```bash
npm install
```

## Desarrollo

```bash
npm run dev
```

## Build

```bash
npm run build
```

## Tipos de Personal

El sistema maneja tres tipos de personal médico:
- **Doctor**: Médicos especialistas
- **Enfermero**: Personal de enfermería
- **Obstetra**: Especialistas en obstetricia

## Estructura de Datos

Cada miembro del personal tiene:
- `id`: Identificador único
- `nombre`: Nombre completo
- `rol`: "doctor" | "enfermero" | "obstetra"
- `especialidad`: Área de especialización
- `descripcion`: Descripción del profesional
- `aniosExperiencia`: Años de experiencia

## Tecnologías

- React 18
- TypeScript
- Vite
- CSS puro (sin frameworks)
- Context API


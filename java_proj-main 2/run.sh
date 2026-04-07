#!/usr/bin/env bash
set -euo pipefail

ROOT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUT_DIR="$ROOT_DIR/out"
JAVA_BIN="${JAVA_HOME:-/usr/lib/jvm/java-17-openjdk-amd64}/bin/java"
JAVAC_BIN="${JAVA_HOME:-/usr/lib/jvm/java-17-openjdk-amd64}/bin/javac"

mkdir -p "$OUT_DIR"
"$JAVAC_BIN" -d "$OUT_DIR" "$ROOT_DIR/App.java"

# When launched from the VS Code Snap terminal, these GTK/Snap variables can
# leak incompatible theme/runtime libraries into Java Swing.
env -u SNAP \
    -u SNAP_ARCH \
    -u SNAP_COMMON \
    -u SNAP_CONTEXT \
    -u SNAP_COOKIE \
    -u SNAP_DATA \
    -u SNAP_EUID \
    -u SNAP_INSTANCE_NAME \
    -u SNAP_LAUNCHER_ARCH_TRIPLET \
    -u SNAP_LIBRARY_PATH \
    -u SNAP_NAME \
    -u SNAP_REAL_HOME \
    -u SNAP_REVISION \
    -u SNAP_UID \
    -u SNAP_USER_COMMON \
    -u SNAP_USER_DATA \
    -u SNAP_VERSION \
    -u GTK_PATH \
    -u GTK_EXE_PREFIX \
    -u GTK_IM_MODULE_FILE \
    -u GTK_MODULES \
    -u GDK_PIXBUF_MODULEDIR \
    -u GDK_PIXBUF_MODULE_FILE \
    -u LD_LIBRARY_PATH \
    "$JAVA_BIN" -cp "$OUT_DIR" App

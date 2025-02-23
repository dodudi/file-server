#!/bin/bash

VERSION=$1
REPOSITORY=$2

cd file-server
mv build/libs/*.jar docker/

if ! docker buildx ls | grep -q "mybuilder"; then
    echo "🔧 'mybuilder' 빌더가 없음. 새로 생성합니다..."
    docker buildx create --name mybuilder --use
else
    echo "✅ 'mybuilder' 빌더가 이미 존재함. 사용합니다..."
    docker buildx use mybuilder
fi

docker buildx create --name mybuilder --use
docker buildx build --platform linux/amd64,linux/arm64 -t $REPOSITORY/file-server:$VERSION --push ./docker
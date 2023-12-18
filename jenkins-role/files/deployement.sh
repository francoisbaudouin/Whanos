LANGUAGE=()
REGISTRY=europe-west9-docker.pkg.dev/hippopothanos

if [[ -f Makefile ]]; then
	LANGUAGE+=("c")
fi
if [[ -f app/pom.xml ]]; then
	LANGUAGE+=("java")
fi
if [[ -f package.json ]]; then
	LANGUAGE+=("javascript")
fi
if [[ -f requirements.txt ]]; then
	LANGUAGE+=("python")
fi
if [[ $(find app -type f) == app/main.bf ]]; then
	LANGUAGE+=("befunge")
fi

if [[ ${#LANGUAGE[@]} == 0 ]]; then
	echo "Invalid project: no language matched."
	exit 1
fi
if [[ ${#LANGUAGE[@]} != 1 ]]; then
	echo "Invalid project: too many languages matched."
	exit 1
fi

# $1: the name of the job
image_name=whanos-$1-${LANGUAGE[0]}
imageName=$REGISTRY/whanos/whanos-$1-${LANGUAGE[0]}

if [[ -f Dockerfile ]]; then
	docker build . -t $image_name
    docker tag $image_name:latest europe-west9-docker.pkg.dev/Hippopothanos/whanos/$image_name:latest
    docker push europe-west9-docker.pkg.dev/Hippopothanos/whanos/$image_name:latest
else
	docker build . \
		-f /home/jenkins/images/${LANGUAGE[0]}/Dockerfile.standalone \
		-t $image_name-standalone
    docker tag $image_name-standalone:latest europe-west9-docker.pkg.dev/Hippopothanos/whanos/$image_name-standalone:latest
    docker push europe-west9-docker.pkg.dev/Hippopothanos/whanos/$image_name-standalone:latest
fi

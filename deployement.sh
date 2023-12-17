LANGUAGE=()

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

imageName="whanos-${LANGUAGE[0]}"

# $1: the name of the job
image_name=$REGISTRY/whanos/whanos-$1-${LANGUAGE[0]}

if [[ -f Dockerfile ]]; then
	docker build . -t $image_name
else
	docker build . \
		-f /images/${LANGUAGE[0]}/Dockerfile.standalone \
		-t $image_name
fi

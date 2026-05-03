import { Canvas } from "@react-three/fiber";
import { useRef } from "react";
import { useFrame } from "@react-three/fiber";

function Sphere() {
    const ref = useRef();

    useFrame(() => {
        ref.current.rotation.y += 0.0006;
    });

    return (
        <mesh ref={ref}>
            <sphereGeometry args={[1.1, 32, 32]} />
            <meshStandardMaterial wireframe color="#00ffff" />
        </mesh>
    );
}

export default function ThreeBackground() {
    return (
        <div className="fixed inset-0 -z-10">
            <Canvas>
                <ambientLight intensity={0.4} />
                <pointLight position={[5, 5, 5]} />
                <Sphere />
            </Canvas>
        </div>
    );
}